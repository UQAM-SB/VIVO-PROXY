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
 * Vivo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-10-07T14:58:04.779-04:00[America/New_York]")public class Vivo   {
  @JsonProperty("iri")
  private String iri = null;

  @JsonProperty("label")
  private String label = null;

  @JsonProperty("type")
  private String type = null;

  public Vivo iri(String iri) {
    this.iri = iri;
    return this;
  }

  /**
   * Get iri
   * @return iri
   **/
  @JsonProperty("iri")
  @Schema(example = "http://localhost:8080/vivo/individual/n6870", required = true, description = "")
  @NotNull
  public String getIri() {
    return iri;
  }

  public void setIri(String iri) {
    this.iri = iri;
  }

  public Vivo label(String label) {
    this.label = label;
    return this;
  }

  /**
   * Get label
   * @return label
   **/
  @JsonProperty("label")
  @Schema(example = "a label", description = "")
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Vivo type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @JsonProperty("type")
  @Schema(example = "http://vivoweb.org/ontology/core#FacultyMember", description = "")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vivo vivo = (Vivo) o;
    return Objects.equals(this.iri, vivo.iri) &&
        Objects.equals(this.label, vivo.label) &&
        Objects.equals(this.type, vivo.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(iri, label, type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Vivo {\n");
    
    sb.append("    iri: ").append(toIndentedString(iri)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
