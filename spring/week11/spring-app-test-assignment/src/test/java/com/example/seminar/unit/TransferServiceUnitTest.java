
package com.example.seminar.unit;

import com.example.seminar.domain.Account;
import com.example.seminar.exception.AccountNotFoundException;
import com.example.seminar.exception.InvalidTransferException;
import com.example.seminar.repository.AccountRepository;
import com.example.seminar.service.TransferService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceUnitTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    TransferService transferService;

    // =====================================================
    // [실습 1-1] 정상 이체 플로우
    // =====================================================

    @Test
    @DisplayName("정상적인 계좌 이체에 성공한다")
    void transferSuccess() {

        // Given
        Account sender = new Account(
                1L, "john", new BigDecimal("1000")
        );

        Account receiver = new Account(
                2L, "jane", new BigDecimal("1000")
        );

        given(accountRepository.findById(1L))
                .willReturn(Optional.of(sender));

        given(accountRepository.findById(2L))
                .willReturn(Optional.of(receiver));

        // When
        transferService.transferMoney(
                1L, 2L, new BigDecimal("100")
        );

        // Then
        assertThat(sender.getAmount())
                .isEqualByComparingTo("900");

        assertThat(receiver.getAmount())
                .isEqualByComparingTo("1100");

        verify(accountRepository).findById(1L);
        verify(accountRepository).findById(2L);
    }


    // =====================================================
    // [실습 1-2] 예외 플로우 - 발신인 계좌 없음
    // =====================================================

    @Test
    @DisplayName("발신인 계좌가 없으면 예외가 발생한다")
    void senderNotFound() {

        // Given
        given(accountRepository.findById(999L))
                .willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() ->
                transferService.transferMoney(
                        999L, 2L, new BigDecimal("100")
                )
        )
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessageContaining("Sender");

        verify(accountRepository).findById(999L);
        verify(accountRepository, never()).findById(2L);
    }


    // =====================================================
    // [실습 1-3] 예외 플로우 - 수취인 계좌 없음
    // =====================================================

    @Test
    @DisplayName("수취인 계좌가 없으면 예외가 발생한다")
    void receiverNotFound() {

        // Given
        Account sender = new Account(
                1L, "John", new BigDecimal("1000")
        );

        given(accountRepository.findById(1L))
                .willReturn(Optional.of(sender));

        given(accountRepository.findById(999L))
                .willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() ->
                transferService.transferMoney(
                        1L, 999L, new BigDecimal("100")
                )
        )
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessageContaining("Receiver");

        assertThat(sender.getAmount())
                .isEqualByComparingTo("1000");

        verify(accountRepository).findById(1L);
        verify(accountRepository).findById(999L);
    }


    // =====================================================
    // [과제 1] 예외 플로우 - 잔액 부족
    // =====================================================

    @Test
    @DisplayName("잔액이 부족하면 이체에 실패한다")
    void insufficientBalance() {

        // 시나리오
        // - 발신인 ID: 1L, 잔액: 100원
        // - 수취인 ID: 2L, 잔액: 1000원
        // - 이체 금액: 500원
        //
        // 예상 결과
        // - InvalidTransferException 발생
        // - 예외 메시지에 "Insufficient balance" 포함
        // - 두 계좌의 잔액이 모두 변경되지 않음

        // Given
        // TODO 4-1: 발신인과 수취인 계좌 생성
        Account sender = new Account(
                1L, "John", new BigDecimal("100")
        );

        Account receiver = new Account(
                2L, "Jane", new BigDecimal("1000")
        );

        // TODO 4-2: Mock Repository 동작 설정
        given(accountRepository.findById(1L))
                .willReturn(Optional.of(sender));

        given(accountRepository.findById(2L))
                .willReturn(Optional.of(receiver));

        // When & Then
        // TODO 4-3: 잔액보다 큰 금액 이체 및 예외 검증
        // TODO 4-4: 예외 메시지 검증
        assertThatThrownBy(() ->
                transferService.transferMoney(
                        1L, 2L, new BigDecimal("500")
                )
        )
                .isInstanceOf(InvalidTransferException.class)
                .hasMessageContaining("Insufficient balance");

        // TODO 4-5: 이체 실패 후 잔액 유지 검증
        assertThat(sender.getAmount())
                .isEqualByComparingTo("100");

        assertThat(receiver.getAmount())
                .isEqualByComparingTo("1000");

        // Repository 조회 여부 검증
        verify(accountRepository).findById(1L);
        verify(accountRepository).findById(2L);
    }


    // =====================================================
    // [과제 2] 예외 플로우 - 음수 금액
    // =====================================================

    @Test
    @DisplayName("음수 금액은 이체할 수 없다")
    void negativeAmount() {

        // 시나리오
        // - 발신인 ID: 1L
        // - 수취인 ID: 2L
        // - 이체 금액: -100원
        //
        // 예상 결과
        // - InvalidTransferException 발생
        // - 예외 메시지에 "positive" 포함
        // - Repository와의 상호작용이 발생하지 않음

        // When & Then
        // TODO 5-1: 음수 금액 이체 및 예외 검증
        // TODO 5-2: 예외 메시지 검증
        assertThatThrownBy(() ->
                transferService.transferMoney(
                        1L, 2L, new BigDecimal("-100")
                )
        )
                .isInstanceOf(InvalidTransferException.class)
                .hasMessageContaining("positive");

        // TODO 5-3: Repository 호출되지 않았는지 검증
        verifyNoInteractions(accountRepository);
    }
}
