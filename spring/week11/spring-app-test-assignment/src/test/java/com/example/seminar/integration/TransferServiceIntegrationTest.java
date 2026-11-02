
package com.example.seminar.integration;

import com.example.seminar.domain.Account;
import com.example.seminar.exception.AccountNotFoundException;
import com.example.seminar.repository.AccountRepository;
import com.example.seminar.service.TransferService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class TransferServiceIntegrationTest {

    @Autowired
    TransferService transferService;

    @Autowired
    AccountRepository accountRepository;

    // =====================================================
    // [실습] 테스트 데이터 정리
    // =====================================================

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
    }


    // =====================================================
    // [실습 1] 정상 이체 플로우
    // =====================================================

    @Test
    @DisplayName("정상적인 계좌 이체에 성공한다")
    void transferSuccess() {

        // Given
        Account sender = accountRepository.save(
                new Account("John", new BigDecimal("1000"))
        );

        Account receiver = accountRepository.save(
                new Account("Jane", new BigDecimal("1000"))
        );

        // When
        transferService.transferMoney(
                sender.getId(),
                receiver.getId(),
                new BigDecimal("100")
        );

        // Then
        Account updatedSender = accountRepository
                .findById(sender.getId())
                .orElseThrow();

        Account updatedReceiver = accountRepository
                .findById(receiver.getId())
                .orElseThrow();

        assertThat(updatedSender.getAmount())
                .isEqualByComparingTo("900");

        assertThat(updatedReceiver.getAmount())
                .isEqualByComparingTo("1100");
    }


    // =====================================================
    // [실습 2] 예외 플로우 - 발신인 계좌 없음
    // =====================================================

    @Test
    @DisplayName("발신인 계좌가 없으면 예외가 발생한다")
    void senderNotFound() {

        // Given
        // 존재하지 않는 발신인 ID 사용
        Long senderId = 999L;

        assertThat(accountRepository.existsById(senderId))
                .isFalse();

        // When & Then
        assertThatThrownBy(() ->
                transferService.transferMoney(
                        senderId,
                        2L,
                        new BigDecimal("100")
                )
        )
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessageContaining("Sender");
    }


    // =====================================================
    // [과제 3] 예외 플로우 - 수취인 계좌 없음
    // =====================================================

    @Test
    @DisplayName("수취인 계좌가 없으면 예외가 발생한다")
    void receiverNotFound() {

        // 시나리오
        // - 발신인 계좌는 실제 DB에 존재
        // - 발신인 잔액: 1000원
        // - 수취인 계좌는 존재하지 않음
        // - 이체 금액: 100원
        //
        // 예상 결과
        // - AccountNotFoundException 발생
        // - 예외 메시지에 "Receiver" 포함
        // - 발신인의 잔액이 1000원으로 유지

        // Given
        // 실제 H2 DB에 발신인 계좌 저장
        Account sender = accountRepository.save(
                new Account("John", new BigDecimal("1000"))
        );

        // 존재하지 않는 수취인 ID
        Long receiverId = 999L;

        // 발신인 계좌 존재 여부 확인
        assertThat(accountRepository.existsById(sender.getId()))
                .isTrue();

        // 수취인 계좌가 존재하지 않는지 확인
        assertThat(accountRepository.existsById(receiverId))
                .isFalse();

        // When & Then
        // 수취인 계좌가 없는 상태에서 이체 시도
        assertThatThrownBy(() ->
                transferService.transferMoney(
                        sender.getId(),
                        receiverId,
                        new BigDecimal("100")
                )
        )
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessageContaining("Receiver");

        // 이체 실패 후 실제 DB에서 발신인 계좌 재조회
        Account updatedSender = accountRepository
                .findById(sender.getId())
                .orElseThrow();

        // 발신인의 잔액이 변경되지 않았는지 검증
        assertThat(updatedSender.getAmount())
                .isEqualByComparingTo("1000");

        // 수취인 계좌가 여전히 존재하지 않는지 확인
        assertThat(accountRepository.existsById(receiverId))
                .isFalse();
    }
}
