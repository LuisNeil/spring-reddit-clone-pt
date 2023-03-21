package org.ltejeda.springredditclone.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ltejeda.springredditclone.dto.SubredditDto;
import org.ltejeda.springredditclone.model.Subreddit;
import org.ltejeda.springredditclone.repository.SubredditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        Subreddit subreddit = subredditRepository.save(mapSubredditDto(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
        return Subreddit.builder()
                .name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll().stream().map(this::mapToDto).collect(toList());
    }

    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder().id(subreddit.getId()).numberOfPosts(subreddit.getPosts().size()).build();
    }
}
