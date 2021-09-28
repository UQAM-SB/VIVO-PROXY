/*
 * VIVO Proxy API
 * Proxy API for VIVO Data Manipulation
 *
 * OpenAPI spec version: 1.0.0
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
 * LinguisticLabel
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-09-26T07:33:17.171-04:00[America/New_York]")public class LinguisticLabel   {
  @JsonProperty("label")
  private String label = null;

  @JsonProperty("language")
  private String language = "en-US";

  public LinguisticLabel label(String label) {
    this.label = label;
    return this;
  }

  /**
   * Get label
   * @return label
   **/
  @JsonProperty("label")
  @Schema(example = "semantic web", required = true, description = "")
  @NotNull
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public LinguisticLabel language(String language) {
    this.language = language;
    return this;
  }

  /**
   * Using the &#x27;Language Tags and Locale Identifiers for the World Wide Web&#x27; defined at https://www.w3.org/TR/ltli/ for this field
   * @return language
   **/
  @JsonProperty("language")
  @Schema(example = "fr-CA", required = true, description = "Using the 'Language Tags and Locale Identifiers for the World Wide Web' defined at https://www.w3.org/TR/ltli/ for this field")
  @NotNull
  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LinguisticLabel linguisticLabel = (LinguisticLabel) o;
    return Objects.equals(this.label, linguisticLabel.label) &&
        Objects.equals(this.language, linguisticLabel.language);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, language);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LinguisticLabel {\n");
    
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
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
