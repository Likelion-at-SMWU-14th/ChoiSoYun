package com.likelion.seminar.service;

import com.likelion.seminar.dto.BoardDTO;
import com.likelion.seminar.entitiy.Board;
import com.likelion.seminar.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // Board 생성
    public BoardDTO createBoard(BoardDTO boardDTO) {

        Board board = new Board();
        board.setName(boardDTO.getName());

        Board savedBoard = boardRepository.save(board);

        boardDTO.setId(savedBoard.getId());

        return boardDTO;
    }

    // Board 개별 조회
    public BoardDTO getBoard(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Board를 찾을 수 없습니다."
                        )
                );

        return new BoardDTO(
                board.getId(),
                board.getName()
        );
    }

    // Board 전체 조회
    public List<BoardDTO> getBoards() {

        List<Board> boards = boardRepository.findAll();

        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (Board board : boards) {

            boardDTOList.add(
                    new BoardDTO(
                            board.getId(),
                            board.getName()
                    )
            );
        }

        return boardDTOList;
    }

    // Board 수정
    @Transactional
    public BoardDTO updateBoard(Long id, BoardDTO boardDTO) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Board를 찾을 수 없습니다."
                        )
                );

        board.setName(boardDTO.getName());

        return new BoardDTO(
                board.getId(),
                board.getName()
        );
    }

    // Board 삭제
    @Transactional
    public void deleteBoard(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Board를 찾을 수 없습니다."
                        )
                );

        if (!board.getPosts().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "게시글이 존재하는 Board는 삭제할 수 없습니다."
            );
        }

        boardRepository.delete(board);
    }
}