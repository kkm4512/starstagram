package com.sparta.starstagram.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

// 일반 Page 객체 직렬화시, 필요없는 정보들 까지 함께 클라이언트로 노출되어, 조정하기 위하여 생성
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class JacksonConfig { }
