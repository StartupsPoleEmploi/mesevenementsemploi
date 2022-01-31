package fr.pe.domaine.peactions.payload.response;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String identifiant;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String identifiant, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.identifiant = identifiant;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return identifiant;
    }

    public void setUsername(String identifiant) {
        this.identifiant = identifiant;
    }

    public List<String> getRoles() {
        return roles;
    }
}
