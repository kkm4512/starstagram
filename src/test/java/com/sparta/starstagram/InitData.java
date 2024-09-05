package com.sparta.starstagram;

import com.sparta.starstagram.entity.Follow;
import com.sparta.starstagram.entity.Post;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.model.post.RequestPostDto;
import com.sparta.starstagram.model.user.UserRequestDto;
import com.sparta.starstagram.repository.FollowRepository;
import com.sparta.starstagram.repository.PostRepository;
import com.sparta.starstagram.repository.UserRepository;
import com.sparta.starstagram.util.UtilFind;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@SpringBootTest
public class InitData {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FollowRepository followRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UtilFind utilFind;

    @Test
    @Transactional
    @Rollback(value = false)
    void init(){
        // 테스트용 계정 생성
        for ( int i=1; i<=10; i++ ) {
            UserRequestDto userRequestDto = new UserRequestDto(
                    "test" + i + "@naver.com", // email
                    "test" + i, //username
                    "!@Skdud340" + i // password
                    );
            User user = new User();
            user.updateEmail(userRequestDto.getEmail());
            user.updatePassword(userRequestDto.getPassword());
            user.updateUserName(userRequestDto.getUsername());
            userRepository.save(user);
        }

        // 테스트용 계정 팔로우 시켜보기
        for ( long i=1; i<=10; i++ ) {
            // 1 ~ 10 난수뽑기
            Random random = new Random();
            int randomNumber = random.nextInt(10) + 1;
            User following = utilFind.userFindById(i); // i번째 유저 찾기 (팔로우를 하는 주체)
            for ( long j=1; j<=randomNumber; j++ ) {
                if (i == j ) continue; // 자기 자신이, 자기 자신을 팔로우는 못하기때문에 continue;
                User follower = utilFind.userFindById(j);                 // j번째 유저 찾기 (팔로우 신청 당할 사람)
                Follow follow = new Follow(following, follower); // 연관관계 설정
                followRepository.save(follow);
            }
        }

        // 테스트용 계정 게시글 작성 시켜보기
        for ( long i=1; i<=10; i++ ) {
            // 1 ~ 10 난수뽑기
            Random random = new Random();
            int randomNumber = random.nextInt(10) + 1;
            User user = utilFind.userFindById(i);
            for ( long j=1; j<=randomNumber; j++ ) {
                RequestPostDto postDto = new RequestPostDto(
                        "title" + i, // 제목
                        "detail" + i // 내용
                );
                Post post = new Post(postDto);
                post.addUser(user);
                postRepository.save(post);
            }
        }
    }
}
