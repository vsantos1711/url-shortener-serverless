package com.vsantos.urlshortener;

import java.sql.Date;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlData {

  @NotNull(message = "The original URL cannot be null!")
  @Size(min = 10, max = 200, message = "The original URL must be between 10 and 200 characters")
  private String originalUrl;

  @Future(message = "Expiration Date must be a future day!")
  private Date expirationDate;

}
