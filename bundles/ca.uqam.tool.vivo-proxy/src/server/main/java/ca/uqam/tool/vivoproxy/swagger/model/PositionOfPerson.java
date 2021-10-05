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
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

/**
 * PositionOfPerson
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-09-29T04:39:30.411-04:00[America/New_York]")public class PositionOfPerson   {
  @JsonProperty("personIRI")
  private String personIRI = null;

  @JsonProperty("organisationIRI")
  private String organisationIRI = null;

  @JsonProperty("positionTitleLabel")
  private List<LinguisticLabel> positionTitleLabel = new ArrayList<LinguisticLabel>();

  @JsonProperty("positionTypeIRI")
  private String positionTypeIRI = "http://vivoweb.org/ontology/core#Position";

  @JsonProperty("startField_year")
  private String startFieldYear = null;

  @JsonProperty("endField_year")
  private String endFieldYear = null;

  public PositionOfPerson personIRI(String personIRI) {
    this.personIRI = personIRI;
    return this;
  }

  /**
   * Get personIRI
   * @return personIRI
   **/
  @JsonProperty("personIRI")
  @Schema(example = "http://localhost:8080/vivo/individual/n774", required = true, description = "")
  @NotNull
  public String getPersonIRI() {
    return personIRI;
  }

  public void setPersonIRI(String personIRI) {
    this.personIRI = personIRI;
  }

  public PositionOfPerson organisationIRI(String organisationIRI) {
    this.organisationIRI = organisationIRI;
    return this;
  }

  /**
   * Get organisationIRI
   * @return organisationIRI
   **/
  @JsonProperty("organisationIRI")
  @Schema(example = "http://localhost:8080/vivo/individual/n4762", required = true, description = "")
  @NotNull
  public String getOrganisationIRI() {
    return organisationIRI;
  }

  public void setOrganisationIRI(String organisationIRI) {
    this.organisationIRI = organisationIRI;
  }

  public PositionOfPerson positionTitleLabel(List<LinguisticLabel> positionTitleLabel) {
    this.positionTitleLabel = positionTitleLabel;
    return this;
  }

  public PositionOfPerson addPositionTitleLabelItem(LinguisticLabel positionTitleLabelItem) {
    this.positionTitleLabel.add(positionTitleLabelItem);
    return this;
  }

  /**
   * Get positionTitleLabel
   * @return positionTitleLabel
   **/
  @JsonProperty("positionTitleLabel")
  @Schema(example = "[{\"label\":\"Professeur\",\"language\":\"fr-CA\"},{\"label\":\"Professor\",\"language\":\"en-US\"}]", required = true, description = "")
  @NotNull
  @Valid
  public List<LinguisticLabel> getPositionTitleLabel() {
    return positionTitleLabel;
  }

  public void setPositionTitleLabel(List<LinguisticLabel> positionTitleLabel) {
    this.positionTitleLabel = positionTitleLabel;
  }

  public PositionOfPerson positionTypeIRI(String positionTypeIRI) {
    this.positionTypeIRI = positionTypeIRI;
    return this;
  }

  /**
   * Get positionTypeIRI
   * @return positionTypeIRI
   **/
  @JsonProperty("positionTypeIRI")
  @Schema(example = "http://vivoweb.org/ontology/core#Position", required = true, description = "")
  @NotNull
  public String getPositionTypeIRI() {
    return positionTypeIRI;
  }

  public void setPositionTypeIRI(String positionTypeIRI) {
    this.positionTypeIRI = positionTypeIRI;
  }

  public PositionOfPerson startFieldYear(String startFieldYear) {
    this.startFieldYear = startFieldYear;
    return this;
  }

  /**
   * Get startFieldYear
   * @return startFieldYear
   **/
  @JsonProperty("startField_year")
  @Schema(description = "")
  public String getStartFieldYear() {
    return startFieldYear;
  }

  public void setStartFieldYear(String startFieldYear) {
    this.startFieldYear = startFieldYear;
  }

  public PositionOfPerson endFieldYear(String endFieldYear) {
    this.endFieldYear = endFieldYear;
    return this;
  }

  /**
   * Get endFieldYear
   * @return endFieldYear
   **/
  @JsonProperty("endField_year")
  @Schema(description = "")
  public String getEndFieldYear() {
    return endFieldYear;
  }

  public void setEndFieldYear(String endFieldYear) {
    this.endFieldYear = endFieldYear;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PositionOfPerson positionOfPerson = (PositionOfPerson) o;
    return Objects.equals(this.personIRI, positionOfPerson.personIRI) &&
        Objects.equals(this.organisationIRI, positionOfPerson.organisationIRI) &&
        Objects.equals(this.positionTitleLabel, positionOfPerson.positionTitleLabel) &&
        Objects.equals(this.positionTypeIRI, positionOfPerson.positionTypeIRI) &&
        Objects.equals(this.startFieldYear, positionOfPerson.startFieldYear) &&
        Objects.equals(this.endFieldYear, positionOfPerson.endFieldYear);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personIRI, organisationIRI, positionTitleLabel, positionTypeIRI, startFieldYear, endFieldYear);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PositionOfPerson {\n");
    
    sb.append("    personIRI: ").append(toIndentedString(personIRI)).append("\n");
    sb.append("    organisationIRI: ").append(toIndentedString(organisationIRI)).append("\n");
    sb.append("    positionTitleLabel: ").append(toIndentedString(positionTitleLabel)).append("\n");
    sb.append("    positionTypeIRI: ").append(toIndentedString(positionTypeIRI)).append("\n");
    sb.append("    startFieldYear: ").append(toIndentedString(startFieldYear)).append("\n");
    sb.append("    endFieldYear: ").append(toIndentedString(endFieldYear)).append("\n");
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
