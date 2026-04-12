from django.contrib import admin, messages
from django.utils.html import format_html
from django.utils.timesince import timesince
from django.utils import timezone
from django.urls import path, reverse
from django.shortcuts import redirect, get_object_or_404

from .models import Story


@admin.register(Story)
class StoryAdmin(admin.ModelAdmin):
    list_display = ['id', 'story_preview', 'uploaded_ago', 'likes_display', 'like_button']
    list_filter = ['created_at']
    search_fields = ['title']
    search_help_text = '스토리 제목으로 검색할 수 있습니다.'

    readonly_fields = ['likes', 'created_at', 'like_action_button']
    fields = ['title', 'image', 'created_at', 'likes', 'like_action_button']

    def get_urls(self):
        urls = super().get_urls()
        custom_urls = [
            path(
                '<int:story_id>/like/',
                self.admin_site.admin_view(self.like_story),
                name='stories_story_like',
            ),
        ]
        return custom_urls + urls

    def like_story(self, request, story_id):
        story = get_object_or_404(Story, pk=story_id)
        story.likes += 1
        story.save()
        self.message_user(request, f'"{story.title}" 좋아요가 1 증가했습니다.', messages.SUCCESS)
        return redirect(request.META.get('HTTP_REFERER', reverse('admin:stories_story_changelist')))

    def story_preview(self, obj):
        if obj.image:
            return format_html(
                '''
                <div style="display:flex; align-items:center; gap:10px;">
                    <a href="{}" target="_blank">
                        <img src="{}" style="width:60px; height:60px; object-fit:cover; border-radius:50%; border:1px solid #ccc; cursor:pointer;" />
                    </a>
                    <div>
                        <div style="font-weight:600;">{}</div>
                    </div>
                </div>
                ''',
                obj.image.url,
                obj.image.url,
                obj.title
            )
        return obj.title
    story_preview.short_description = '스토리'

    def uploaded_ago(self, obj):
        return f"{timesince(obj.created_at, timezone.now()).split(',')[0]} 전"
    uploaded_ago.short_description = '업로드 시간'

    def likes_display(self, obj):
        return format_html(
            '<span style="background:#ffe6e6; color:#d6336c; padding:6px 12px; border-radius:12px; font-weight:600;">❤️ {}</span>',
            obj.likes
        )
    likes_display.short_description = '좋아요'

    def like_button(self, obj):
        url = reverse('admin:stories_story_like', args=[obj.id])
        return format_html(
            '<a href="{}" style="background:#ff4d6d; color:white; padding:6px 12px; border-radius:8px; text-decoration:none; font-weight:600;">좋아요 +1</a>',
            url
        )
    like_button.short_description = '좋아요 누르기'

    def like_action_button(self, obj):
        if obj and obj.id:
            url = reverse('admin:stories_story_like', args=[obj.id])
            return format_html(
                '<a href="{}" style="background:#ff4d6d; color:white; padding:8px 14px; border-radius:8px; text-decoration:none; font-weight:600;">❤️ 좋아요 +1</a>',
                url
            )
        return '저장 후 사용 가능합니다.'
    like_action_button.short_description = '좋아요 버튼'