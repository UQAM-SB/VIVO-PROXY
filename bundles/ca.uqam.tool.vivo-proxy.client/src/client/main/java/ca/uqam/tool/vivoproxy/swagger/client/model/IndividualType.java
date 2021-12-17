/*
 * VIVO Proxy API
 * Proxy API for VIVO Data Manipulation
 *
 * OpenAPI spec version: 1.0.0 - 2021-10-23
 * Contact: vivo@uqam.ca
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package ca.uqam.tool.vivoproxy.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
/**
 * IndividualType
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-12-16T08:26:24.631-05:00[America/New_York]")
public class IndividualType {
  @SerializedName("individualIRI")
  private String individualIRI = null;

  @SerializedName("vivoTypeIRI")
  private String vivoTypeIRI = null;

  public IndividualType individualIRI(String individualIRI) {
    this.individualIRI = individualIRI;
    return this;
  }

   /**
   * Get individualIRI
   * @return individualIRI
  **/
  @Schema(example = "http://localhost:8080/vivo/individual/n6870", required = true, description = "")
  public String getIndividualIRI() {
    return individualIRI;
  }

  public void setIndividualIRI(String individualIRI) {
    this.individualIRI = individualIRI;
  }

  public IndividualType vivoTypeIRI(String vivoTypeIRI) {
    this.vivoTypeIRI = vivoTypeIRI;
    return this;
  }

   /**
   * Get vivoTypeIRI
   * @return vivoTypeIRI
  **/
  @Schema(example = "http://vivoweb.org/ontology/core#FacultyMember", required = true, description = "")
  public String getVivoTypeIRI() {
    return vivoTypeIRI;
  }

  public void setVivoTypeIRI(String vivoTypeIRI) {
    this.vivoTypeIRI = vivoTypeIRI;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IndividualType individualType = (IndividualType) o;
    return Objects.equals(this.individualIRI, individualType.individualIRI) &&
        Objects.equals(this.vivoTypeIRI, individualType.vivoTypeIRI);
  }

  @Override
  public int hashCode() {
    return Objects.hash(individualIRI, vivoTypeIRI);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IndividualType {\n");
    
    sb.append("    individualIRI: ").append(toIndentedString(individualIRI)).append("\n");
    sb.append("    vivoTypeIRI: ").append(toIndentedString(vivoTypeIRI)).append("\n");
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
