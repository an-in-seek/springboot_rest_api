package com.web.seek.controller;

import com.web.seek.domain.Board;
import com.web.seek.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardRestController {

    private final BoardService boardService;

    /**
     *
     * @param pageable
     * @param assembler
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBoards(@PageableDefault Pageable pageable, PagedResourcesAssembler<Board> assembler) {
        Page<Board> boards = boardService.findAll(pageable);
        PagedModel<EntityModel<Board>> model = assembler.toModel(boards);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     *
     * @param board
     * @return
     */
    @PostMapping
    public ResponseEntity<?> postBoard(@RequestBody Board board) {
        Board savedBoard = boardService.save(board);
        return new ResponseEntity<>(savedBoard, HttpStatus.CREATED);
    }

    /**
     *
     * @param id
     * @param board
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> putBoard(@PathVariable("id") Long id, @RequestBody Board board) {
        Board persistBoard = boardService.findBoardById(id);
        persistBoard.update(board);
        Board savedBoard = boardService.save(persistBoard);
        return new ResponseEntity<>(savedBoard, HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
