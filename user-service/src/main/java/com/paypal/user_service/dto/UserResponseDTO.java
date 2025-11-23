package com.paypal.user_service.dto;

public record UserResponseDTO(Long id, String username, String email) {
    //will have constructor, getter
}

/*public class UserResponseDTO {
    private Long userId;
    private String name;
    private String email;

    public UserResponseDTO(Long id, String name, String email) {
        this.userId = id;
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}*/
