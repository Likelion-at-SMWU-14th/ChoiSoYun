from django.contrib.auth.models import User
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import status

from .serializers import UserModelSerializer


@api_view(['POST'])
def signup_api_view(request):
    username = request.data.get('username')
    email = request.data.get('email')
    password = request.data.get('password')

    if not username or not password:
        return Response(
            {'error': 'username과 password는 필수입니다.'},
            status=status.HTTP_400_BAD_REQUEST
        )

    if User.objects.filter(username=username).exists():
        return Response(
            {'error': '이미 존재하는 username입니다.'},
            status=status.HTTP_400_BAD_REQUEST
        )

    user = User.objects.create_user(
        username=username,
        email=email,
        password=password
    )

    serializer = UserModelSerializer(user)

    return Response(
        serializer.data,
        status=status.HTTP_201_CREATED
    )

@api_view(['GET', 'PUT', 'DELETE'])
def user_detail_api_view(request, user_id):
    try:
        user = User.objects.get(id=user_id)
    except User.DoesNotExist:
        return Response(
            {'error': '해당 회원을 찾을 수 없습니다.'},
            status=status.HTTP_404_NOT_FOUND
        )

    # 회원정보 조회
    if request.method == 'GET':
        serializer = UserModelSerializer(user)

        return Response(
            serializer.data,
            status=status.HTTP_200_OK
        )

    # 회원정보 수정
    elif request.method == 'PUT':
        serializer = UserModelSerializer(
            user,
            data=request.data
        )

        if serializer.is_valid():
            serializer.save()

            return Response(
                serializer.data,
                status=status.HTTP_200_OK
            )

        return Response(
            serializer.errors,
            status=status.HTTP_400_BAD_REQUEST
        )

    # 회원 탈퇴
    elif request.method == 'DELETE':
        user.delete()

        return Response(
            status=status.HTTP_204_NO_CONTENT
        )