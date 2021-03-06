/*
 * VIVO Proxy API
 * Proxy API for VIVO Data Manipulation
 *
 * OpenAPI spec version: 1.0.0 - 2021-10-07
 * Contact: vivo@uqam.ca
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package ca.uqam.tool.vivoproxy.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;
import javax.validation.Valid;

/**
 * Image
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-10-07T14:58:04.779-04:00[America/New_York]")public class Image   {
  @JsonProperty("individualIRI")
  private String individualIRI = null;

  @JsonProperty("imageURL")
  private String imageURL = null;

  @JsonProperty("orig_X")
  private Integer origX = 0;

  @JsonProperty("orig_Y")
  private Integer origY = 0;

  @JsonProperty("width")
  private Integer width = 270;

  @JsonProperty("height")
  private Integer height = 270;

  public Image individualIRI(String individualIRI) {
    this.individualIRI = individualIRI;
    return this;
  }

  /**
   * Get individualIRI
   * @return individualIRI
   **/
  @JsonProperty("individualIRI")
  @Schema(example = "http://localhost:8080/vivo/individual/n7440", required = true, description = "")
  @NotNull
  public String getIndividualIRI() {
    return individualIRI;
  }

  public void setIndividualIRI(String individualIRI) {
    this.individualIRI = individualIRI;
  }

  public Image imageURL(String imageURL) {
    this.imageURL = imageURL;
    return this;
  }

  /**
   * Get imageURL
   * @return imageURL
   **/
  @JsonProperty("imageURL")
  @Schema(example = "/home/user/photo/picture.jpg", required = true, description = "")
  @NotNull
  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

  public Image origX(Integer origX) {
    this.origX = origX;
    return this;
  }

  /**
   * Get origX
   * @return origX
   **/
  @JsonProperty("orig_X")
  @Schema(description = "")
  public Integer getOrigX() {
    return origX;
  }

  public void setOrigX(Integer origX) {
    this.origX = origX;
  }

  public Image origY(Integer origY) {
    this.origY = origY;
    return this;
  }

  /**
   * Get origY
   * @return origY
   **/
  @JsonProperty("orig_Y")
  @Schema(description = "")
  public Integer getOrigY() {
    return origY;
  }

  public void setOrigY(Integer origY) {
    this.origY = origY;
  }

  public Image width(Integer width) {
    this.width = width;
    return this;
  }

  /**
   * Get width
   * @return width
   **/
  @JsonProperty("width")
  @Schema(required = true, description = "")
  @NotNull
  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Image height(Integer height) {
    this.height = height;
    return this;
  }

  /**
   * Get height
   * @return height
   **/
  @JsonProperty("height")
  @Schema(required = true, description = "")
  @NotNull
  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Image image = (Image) o;
    return Objects.equals(this.individualIRI, image.individualIRI) &&
        Objects.equals(this.imageURL, image.imageURL) &&
        Objects.equals(this.origX, image.origX) &&
        Objects.equals(this.origY, image.origY) &&
        Objects.equals(this.width, image.width) &&
        Objects.equals(this.height, image.height);
  }

  @Override
  public int hashCode() {
    return Objects.hash(individualIRI, imageURL, origX, origY, width, height);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Image {\n");
    
    sb.append("    individualIRI: ").append(toIndentedString(individualIRI)).append("\n");
    sb.append("    imageURL: ").append(toIndentedString(imageURL)).append("\n");
    sb.append("    origX: ").append(toIndentedString(origX)).append("\n");
    sb.append("    origY: ").append(toIndentedString(origY)).append("\n");
    sb.append("    width: ").append(toIndentedString(width)).append("\n");
    sb.append("    height: ").append(toIndentedString(height)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
