I shall start first by explaining, how to bring in Spring Security into your application.

Just add below dependency to your application. Now, when you run your application the spring security is implemented by default. (As of April 2021, version might change in future)

```
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-core</artifactId>
    <version>5.4.5</version>
</dependency>
```

Closely looking at the console, you will see a password generated for default user: **user**. The password is a hash that you need to use.

When you access any URL from your application now, you will be restricted from Postman. From your browser, you will see a login page where you need to enter this username and password and you will be through to your URL. That sets up the inbuilt Spring Security.

But what is happening under the hood?

I shall answer it by reminding you of Servlets and Filters and DispatcherServlet in Spring.

DispatcherServlet is the very basic of Spring MVC and it forwards the requests to your controllers. Basically, DispatcherServlet is also a servlet.


I can create a chain of filters before DispatcherServlet and check my request for Authentication and Authorization before forwarding the request to hit my DispatcherServlet and then my controllers. This way, I can bring in Security to my application. This is exactly what the Spring Security does.

The below link very delicately highlights all the filters that are there before DispatcherServlet and what is the importance of those Filters. Please refer the link below:

https://stackoverflow.com/questions/41480102/how-spring-security-filter-chain-works


Now, we need to understand what authentication and authorization is:
1. Authentication- Anyone using your application needs to have some info and you need to verify that user’s username, password to allow him to access your application. If his username or password is wrong, that means he is not authenticated.
2. Authorization- Once the user is authenticated, there might be some URLs of your application that should only be allowed to admin users and not normal users. This is called authorizing a user to access some parts of your application based on his role.


Let us look at some important Spring’s Filter in Filter Chain:


**•	BasicAuthenticationFilter:** Tries to find a Basic Auth HTTP Header on the request and if found, tries to authenticate the user with the header’s username and password.


**•	UsernamePasswordAuthenticationFilter:** Tries to find a username/password request parameter/POST body and if found, tries to authenticate the user with those values.


**•	DefaultLoginPageGeneratingFilter:** Generates a login page for you, if you don’t explicitly disable that feature. THIS filter is why you get a default login page when enabling Spring Security.


**•	DefaultLogoutPageGeneratingFilter:** Generates a logout page for you, if you don’t explicitly disable that feature.


**•	FilterSecurityInterceptor:** Does your authorization.


These filters, by default, are providing you a login page which you saw on your browser. Also, they provide a logout page, ability to login with Basic Auth or Form Logins, as well as protecting against CSRF attacks.

Remember, the login page at the beginning just after adding Spring Security to your pom.xml. That is happening because of the below class:
```
public abstract class WebSecurityConfigurerAdapter implements
                WebSecurityConfigurer<WebSecurity> {

    protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                .formLogin().and()
                .httpBasic();
        }
}
```
This WebSecurityConfigurerAdapter class is what we extend and we override its configure method. As per above, all the requests need to do basic authentication via form login method. This login page is the default provided by Spring that we saw when we accessed our URL.


Now, next question arises, what if we want to do this configuration ourselves? The below topic discusses exactly that:

**How to configure Spring Security?**


To configure Spring Security, we need to have a @Configuration, @EnableWebSecurity class which extends WebSecurityConfigurerAdapter class.
```
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http
        .authorizeRequests()
          .antMatchers("/", "/home").permitAll()
          .anyRequest().authenticated()
          .and()
       .formLogin()
         .loginPage("/login")
         .permitAll()
         .and()
      .logout()
        .permitAll()
        .and()
      .httpBasic();
  }
}
```
You must do above the mentioned configurations. Now, you can do your specific security configuration i.e. which all URLs are allowed, which need to be authenticated, what are the types of authentication the application will perform and what are the roles that are allowed on specific URLs.

So, basically, all your authentication and authorization information is configured here. Other configuration regarding CORS, CSRF and other exploits is also done here, but that is out of the scope of the basics.


In the example above, all requests going to **/** and **/home** are allowed to any user i.e. anyone can access them and get response but the other requests need to be authenticated. Also, we have allowed form login i.e. when any request apart from **/** and **/home** is accessed, the user will be presented with a login page where he will input his username and password and that username/password will be authenticated using basic authentication i.e. sending in an HTTP Basic Auth Header to authenticate.



Till now, we have added Spring Security, protected our URLs, configured Spring Security. But, how will we check the username and password to be authenticated? The below discusses this:


You need to specify some @Beans to get Spring Security working. Why some beans are needed?
**Because** **Spring Container needs these beans to implement security under the hood.**

You need to provide these two beans – UserDetailsService & PasswordEncoder.


**UserDetailsService** **–** This is responsible for providing your user to the Spring container. The user can be present either in your DB, memory, anywhere. Ex: It can be stored in User table with username, password, roles and other columns.

```
@Bean
public UserDetailsService userDetailsService() {
    return new MyUserDetailsService();
}
```

Above, we are providing our custom MyUserDetailsService which has to be a UserDetailsService child for Spring container to identify its purpose. Below is the sample implementation:
```
public class MyDatabaseUserDetailsService implements UserDetailsService {

        UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
         //  Load the user from the users table by username. If not found, throw UsernameNotFoundException.
         // Convert/wrap the user to a UserDetails object and return it.
        return someUserDetails;
    }
}
```
```
public interface UserDetails extends Serializable {

    String getUsername();

    String getPassword();

    // isAccountNonExpired,isAccountNonLocked,
    // isCredentialsNonExpired,isEnabled
}
```
You see, UserDetailsService shall provide the container with UserDetails object.


By default, Spring provides these implementations of UserDetailsService:

**1.	JdbcUserDetailsManager-** which is a JDBC based UserDetailsService. You can configure it to match your user table/column structure.

**2.	InMemoryUserDetailsManager-** which keeps all userdetails in memory. This is generally used for testing purposes.

**3.	org.springframework.security.core.userdetail.User–** This is what is used mostly in custom applications. You can extend this User class on your custom implementation for your user object.

Now, as per above if any request arrives and needs to be authenticated, then since we have UserDetailsService in place, we will get the user from the UserDetails object returned by UserDetailsService for the user who has sent the request and can authenticate his sent username/password with the one received from our UserDetailsService.

This way, the user is authenticated.

**Note:** The password received from user is automatically hashed. So, if we do not have the hash representation of password from our UserDetailsService, it will fail even when the password is correct.

To prevent this, we provide PasswordEncoder bean to our container which will apply the hashing algorithm specified by the PasswordEncoder on the password in UserDetails object and make a hash for it. Then, it checks both the hashed passwords and authenticates or fails a user.


**PasswordEncoder-** This provides a hash of your password for security purposes. **Why?** You cannot/should not deal with plain passwords. That beats the very purpose of Spring Security. Better, hash it with any algorithm.

```
@Bean
public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
}
```
Now, you can autowire this PasswordEncoder anywhere in your application.


**AuthenticationProvider-**


In some cases, we do not have access to the user’s password but some other third party stores our user's information in some fancy way.

In those cases, we need to provide AuthenticationProvider beans to our Spring container. Once container has this object, it will try to authenticate with the implementation we have provided to authenticate with that third party which will give us a UserDetails object or any other object from which we can obtain our UserDetails object.

Once, this is obtained, that means we are authenticated and we will send back a UsernamePasswordAuthenticationToken with our username, password and authorities/roles. If it is not obtained, we can throw an exception.

```
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new MyAuthenticationProvider();
    }

```

An AuthenticationProvider consists primarily of one method and a basic implementation could look like this:

```
public class MyAuthenticationProvider implements AuthenticationProvider {

        Authentication authenticate(Authentication authentication)  
                throws AuthenticationException {
            String username = authentication.getPrincipal().toString(); 
            String password = authentication.getCredentials().toString();

            User user = callThirdPartyService(username, password); 
            if (user == null) {                                     
                throw new AuthenticationException("Incorrect username/password");
            }
            return new UserNamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        }
}
```

Thats all there is to Spring Security basics or under the hood functionality and how we can leverage these to customize our security implementation. You can find examples anywhere. More advanced topics such as JWT, Oauth2 implementation, CSRF prevention, CORS allowance are beyond the scope. 