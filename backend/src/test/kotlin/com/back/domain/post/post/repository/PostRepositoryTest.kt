package com.back.domain.post.post.repository

import com.back.standard.enums.PostSearchKeywordType
import com.back.standard.enums.PostSearchSortType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostRepositoryTest {
    @Autowired
    private lateinit var postRepository: PostRepository

    @Test
    @DisplayName("findQPagedByKw")
    fun t5() {
        val postPage = postRepository.findQPagedByKw(
            PostSearchKeywordType.AUTHOR_NICKNAME,
            "유저",
            PageRequest.of(
                0,
                10,
                PostSearchSortType.ID.sortBy
            ),
        )

        val content = postPage.content

        // 게시물이 100개
        println(content[0].title)
        println(content[1].title)
        println(content[2].title)

        // 각 게시물 작성자가 다 서로 다르다고 할 때
        // 100번의 회원 조회 쿼리 발생
        println(content[0].author.nickname)
        println(content[2].author.nickname)

        assertThat(content).isNotEmpty
    }
}