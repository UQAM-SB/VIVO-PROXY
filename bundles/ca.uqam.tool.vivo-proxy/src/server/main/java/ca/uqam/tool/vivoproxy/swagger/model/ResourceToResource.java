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
 * ResourceToResource
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-07-30T09:54:09.106-04:00[America/New_York]")public class ResourceToResource   {
  @JsonProperty("subject-IRI")
  private String subjectIRI = null;

  @JsonProperty("object-IRI")
  private String objectIRI = null;

  public ResourceToResource subjectIRI(String subjectIRI) {
    this.subjectIRI = subjectIRI;
    return this;
  }

  /**
   * Get subjectIRI
   * @return subjectIRI
   **/
  @JsonProperty("subject-IRI")
  @Schema(example = "http://localhost:8080/vivo/individual/n774", required = true, description = "")
  @NotNull
  public String getSubjectIRI() {
    return subjectIRI;
  }

  public void setSubjectIRI(String subjectIRI) {
    this.subjectIRI = subjectIRI;
  }

  public ResourceToResource objectIRI(String objectIRI) {
    this.objectIRI = objectIRI;
    return this;
  }

  /**
   * Get objectIRI
   * @return objectIRI
   **/
  @JsonProperty("object-IRI")
  @Schema(example = "http://purl.org/uqam.ca/vocabulary/expertise#semanticweb", required = true, description = "")
  @NotNull
  public String getObjectIRI() {
    return objectIRI;
  }

  public void setObjectIRI(String objectIRI) {
    this.objectIRI = objectIRI;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResourceToResource resourceToResource = (ResourceToResource) o;
    return Objects.equals(this.subjectIRI, resourceToResource.subjectIRI) &&
        Objects.equals(this.objectIRI, resourceToResource.objectIRI);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subjectIRI, objectIRI);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResourceToResource {\n");
    
    sb.append("    subjectIRI: ").append(toIndentedString(subjectIRI)).append("\n");
    sb.append("    objectIRI: ").append(toIndentedString(objectIRI)).append("\n");
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