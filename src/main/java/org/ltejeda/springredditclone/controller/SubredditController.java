package org.ltejeda.springredditclone.controller;

import lombok.RequiredArgsConstructor;
import org.ltejeda.springredditclone.dto.SubredditDto;
import org.ltejeda.springredditclone.service.SubredditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/subreddit")
@RequiredArgsConstructor
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto){
        return ResponseEntity.status(CREATED).body(subredditService.save(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
        return ResponseEntity.status(OK).body(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSubreddit(@PathVariable Long id){
        return ResponseEntity
                .status(OK)
                .body(subredditService.getSubreddit(id));
    }
}
