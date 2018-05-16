package com.example.demooauth2login;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * The JSON response will be
 *
 * <pre><code>
 * {
 *     "id": "abcedef",
 *     "email": "ichiro@example.com",
 *     "name": {
 *         "givenName": "Ichiro",
 *         "familyName: "Suzuki"
 *     }
 * }
 * </code></pre>
 * 
 * @see org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService
 */
public class HomeOAuth2User implements OAuth2User {
	private final List<GrantedAuthority> authorities = AuthorityUtils
			.createAuthorityList("ROLE_USER");

	private final String id;
	private final String email;
	private final String givenName;
	private final String familyName;
	private final Map<String, Object> attributes;

	@JsonCreator
	public HomeOAuth2User(@JsonProperty("id") String id,
			@JsonProperty("email") String email,
			@JsonProperty("name") Map<String, String> name) {
		this.id = id;
		this.email = email;
		this.givenName = name.get("givenName");
		this.familyName = name.get("familyName");
		Map<String, Object> attr = new LinkedHashMap<>();
		attr.put("id", this.id);
		attr.put("email", this.email);
		attr.put("givenName", this.givenName);
		attr.put("familyName", this.familyName);
		this.attributes = Collections.unmodifiableMap(attr);
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getGivenName() {
		return givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public String getName() {
		return this.email;
	}

	@Override
	public String toString() {
		return "HomeOAuth2User{" + "authorities=" + authorities + ", id='" + id + '\''
				+ ", email='" + email + '\'' + ", givenName='" + givenName + '\''
				+ ", familyName='" + familyName + '\'' + ", attributes=" + attributes
				+ '}';
	}
}
