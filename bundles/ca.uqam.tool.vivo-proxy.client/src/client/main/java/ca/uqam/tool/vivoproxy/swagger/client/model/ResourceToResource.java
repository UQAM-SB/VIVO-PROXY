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
 * ResourceToResource
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-12-16T08:26:24.631-05:00[America/New_York]")
public class ResourceToResource {
  @SerializedName("subject-IRI")
  private String subjectIRI = null;

  @SerializedName("object-IRI")
  private String objectIRI = null;

  public ResourceToResource subjectIRI(String subjectIRI) {
    this.subjectIRI = subjectIRI;
    return this;
  }

   /**
   * Get subjectIRI
   * @return subjectIRI
  **/
  @Schema(example = "http://localhost:8080/vivo/individual/n774", required = true, description = "")
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
  @Schema(example = "http://purl.org/uqam.ca/vocabulary/expertise#semanticweb", required = true, description = "")
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
