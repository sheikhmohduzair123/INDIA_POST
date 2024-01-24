package com.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "postal_user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = false)
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Integer id;
	@NotEmpty(message = "{name.not.empty}")
	@Size(max=50, message = "{name.size.betwn}")
    private String name;
	@NotEmpty(message = "{email.must.not.empty}")
    //@Pattern(regexp = "^[\\w-]+(?:\\.[\\w-]+)*@(?:[\\w-]+\\.)+[a-zA-Z]{2,4}$", message="{email.format.wrong}")
    private String email;
	@NotEmpty(message = "{uname.not.empty}")
    private String username;
	@NotEmpty(message = "{pass.not.empty}")
	//@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,15}$", message = "{pass.strong}")
    private String password;
    private boolean enabled;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    @NotNull
    private Date dob;
    private String activationCode;
    private String identificationId; //pincode+postOffice-id+counter-id
    private Role role;
    private Long rmsId;
    private long postalCode;
    private boolean firstLogin;
    private Date lastLogin;
    private Status status;
    
    private Date createdOn;
    
    private Date updatedOn;
    
    private Integer updatedBy;
    
	private Integer createdBy;
	
	private String addressLine1;

	private String addressLine2;

	private String mobileNumber;

    public User() {
    }

	public User(Integer id,
			@NotEmpty(message = "{name.not.empty}") @Size(max = 50, message = "{name.size.betwn}") String name,
			@NotEmpty(message = "{email.must.not.empty}") String email,
			@NotEmpty(message = "{uname.not.empty}") String username,
			@NotEmpty(message = "{pass.not.empty}") String password, boolean enabled, boolean accountExpired,
			boolean accountLocked, boolean credentialsExpired, @NotNull Date dob, String activationCode,
			String identificationId, Role role, Long rmsId, long postalCode, boolean firstLogin, Date lastLogin,
			Status status) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.accountExpired = accountExpired;
		this.accountLocked = accountLocked;
		this.credentialsExpired = credentialsExpired;
		this.dob = dob;
		this.activationCode = activationCode;
		this.identificationId = identificationId;
		this.role = role;
		this.rmsId = rmsId;
		this.postalCode = postalCode;
		this.firstLogin = firstLogin;
		this.lastLogin = lastLogin;
		this.status = status;
	}

	/*
	 * public User(String name, String email, String password, Date dob, Role role)
	 * { this.name = name; this.email = email; this.password = password; this.dob =
	 * dob; this.role = role; }
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email", length = 50)
    @NotEmpty(message = "{email.not.empty}")
   // @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z]+(\\.[A-Za-z]{2,4})$", message="{email.pattern.wrong}")
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "username", length = 50)
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 90)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "dob", length = 10)
    public Date getDob() {
        return this.dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "role", referencedColumnName="id")
    public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	public Long getRmsId() {
		return rmsId;
	}

	public void setRmsId(Long rmsId) {
		this.rmsId = rmsId;
	}

	public long getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(long postalCode) {
		this.postalCode = postalCode;
	}

	public Status getStatus() {
			return status;
		}

		public void setStatus(Status status) {
			this.status = status;
		}
		
	    public Date getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(Date createdOn) {
			this.createdOn = createdOn;
		}

		public Date getUpdatedOn() {
			return updatedOn;
		}

		public void setUpdatedOn(Date updatedOn) {
			this.updatedOn = updatedOn;
		}

	    public Integer getUpdatedBy() {
			return updatedBy;
		}

		public void setUpdatedBy(Integer updatedBy) {
			this.updatedBy = updatedBy;
		}

		public Integer getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(Integer createdBy) {
			this.createdBy = createdBy;
		}

	@Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Role userRoles = this.getRole();

        if(userRoles != null)
        {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRoles.getName());
                authorities.add(authority);
        }
        return authorities;
    }

	@Column(name="account_enabled")
    public boolean isEnabled() {
        return enabled;
    }

    @Column(name="account_expired")
    public boolean isAccountExpired() {
        return accountExpired;
    }

    @Column(name="account_locked")
    public boolean isAccountLocked() {
        return accountLocked;
    }

    @Column(name="credentials_expired")
    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    @Transient
	@Override
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

    @Transient
	@Override
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

    @Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

    public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getIdentificationId() {
		return identificationId;
	}

	public void setIdentificationId(String identificationId) {
		this.identificationId = identificationId;
	}

	@Column(name="first_login")
	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	@Temporal(TemporalType.DATE)
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (accountExpired ? 1231 : 1237);
		result = prime * result + (accountLocked ? 1231 : 1237);
		result = prime * result
				+ ((activationCode == null) ? 0 : activationCode.hashCode());
		result = prime * result + (credentialsExpired ? 1231 : 1237);
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((identificationId == null) ? 0 : identificationId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (accountExpired != other.accountExpired) {
			return false;
		}
		if (accountLocked != other.accountLocked) {
			return false;
		}
		if (activationCode == null) {
			if (other.activationCode != null) {
				return false;
			}
		} else if (!activationCode.equals(other.activationCode)) {
			return false;
		}
		if (credentialsExpired != other.credentialsExpired) {
			return false;
		}
		if (dob == null) {
			if (other.dob != null) {
				return false;
			}
		} else if (!dob.equals(other.dob)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (enabled != other.enabled) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (identificationId == null) {
			if (other.identificationId != null) {
				return false;
			}
		} else if (!identificationId.equals(other.identificationId)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return (id != null ? id + ", " : "")
				+ (name != null ? name + ", " : "")
				+ (email != null ? email + ", " : "")
				+ (username != null ? username + ", " : "")
				+ (password != null ? password + ", " : "") + enabled + ", "
				+ accountExpired + ", " + accountLocked + ", "
				+ credentialsExpired + ", " + (dob != null ? dob + ", " : "")
				+ (activationCode != null ? activationCode + ", " : "")
				+ (identificationId != null ? identificationId + ", " : "")
				+ (role != null ? role : "");
	}

}

