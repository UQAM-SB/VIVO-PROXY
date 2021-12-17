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
import ca.uqam.tool.vivoproxy.swagger.client.model.LinguisticLabel;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Person
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-12-16T08:26:24.631-05:00[America/New_York]")
public class Person {
  @SerializedName("personType")
  private String personType = null;

  @SerializedName("firstName")
  private List<LinguisticLabel> firstName = new ArrayList<LinguisticLabel>();

  @SerializedName("lastName")
  private List<LinguisticLabel> lastName = new ArrayList<LinguisticLabel>();

  public Person personType(String personType) {
    this.personType = personType;
    return this;
  }

   /**
   * Get personType
   * @return personType
  **/
  @Schema(example = "http://vivoweb.org/ontology/core#FacultyMember", required = true, description = "")
  public String getPersonType() {
    return personType;
  }

  public void setPersonType(String personType) {
    this.personType = personType;
  }

  public Person firstName(List<LinguisticLabel> firstName) {
    this.firstName = firstName;
    return this;
  }

  public Person addFirstNameItem(LinguisticLabel firstNameItem) {
    this.firstName.add(firstNameItem);
    return this;
  }

   /**
   * Get firstName
   * @return firstName
  **/
  @Schema(example = "[{\"label\":\"Peter\",\"language\":\"fr-CA\"},{\"label\":\"Peter\",\"language\":\"en-US\"}]", required = true, description = "")
  public List<LinguisticLabel> getFirstName() {
    return firstName;
  }

  public void setFirstName(List<LinguisticLabel> firstName) {
    this.firstName = firstName;
  }

  public Person lastName(List<LinguisticLabel> lastName) {
    this.lastName = lastName;
    return this;
  }

  public Person addLastNameItem(LinguisticLabel lastNameItem) {
    this.lastName.add(lastNameItem);
    return this;
  }

   /**
   * Get lastName
   * @return lastName
  **/
  @Schema(example = "[{\"label\":\"Jasper\",\"language\":\"fr-CA\"},{\"label\":\"Jasper\",\"language\":\"en-US\"}]", required = true, description = "")
  public List<LinguisticLabel> getLastName() {
    return lastName;
  }

  public void setLastName(List<LinguisticLabel> lastName) {
    this.lastName = lastName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(this.personType, person.personType) &&
        Objects.equals(this.firstName, person.firstName) &&
        Objects.equals(this.lastName, person.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personType, firstName, lastName);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Person {\n");
    
    sb.append("    personType: ").append(toIndentedString(personType)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
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
