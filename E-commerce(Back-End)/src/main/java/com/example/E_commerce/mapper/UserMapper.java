package com.example.E_commerce.mapper;


import com.example.E_commerce.dto.UserDto;
import com.example.E_commerce.model.User;
import org.hibernate.collection.spi.PersistentBag;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        // authorities ve diğer proxy nesnelerini dönüştürmek için converter ekleyin
        modelMapper.addConverter(ctx -> ctx.getSource() == null ? null : new ArrayList<>(ctx.getSource()), PersistentBag.class, List.class);
    }

    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }


}
