package com.avira.iot.interview.common.config;

import com.avira.iot.interview.users.dto.UserDTO;
import com.avira.iot.interview.users.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        addUserMappings(modelMapper);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    private void addUserMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(UserDTO.class, User.class).addMappings(mapper -> {
            mapper.skip(User::setId);
            mapper.map(UserDTO::getUserId, User::setUserId);
        });
    }
}
