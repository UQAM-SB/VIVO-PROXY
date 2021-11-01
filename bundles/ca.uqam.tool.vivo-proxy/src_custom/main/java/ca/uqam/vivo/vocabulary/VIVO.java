package ca.uqam.vivo.vocabulary;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public class VIVO {
	public static final String uri="http://vivoweb.org/ontology/core#";

	protected static final Resource resource( String local )
	{ return ResourceFactory.createResource( uri + local ); }

	protected static final Property property( String local )
	{ return ResourceFactory.createProperty( uri, local ); }

	public static String getURI() {
		return uri;
	}
	/*
	 * SPARQL example to build java statement from vivo.owl
	 * 
	 * 
	SELECT distinct ?cmmd
	WHERE {
    	?s a ?o .
		bind(STRAFTER(str(?s) , "http://vivoweb.org/ontology/core#")  as ?name)
		bind(concat("public static Property  ",?name ,"()        { return property( \"",?name, '"); }') as ?cmmd)
		FILTER (regex(str(?s),"http://vivoweb.org/ontology/core"))
		FILTER (!regex(str(?o),"Class"))
	}
	 */
	public static final Property abbreviation = Init.abbreviation();
	public static final Property affiliatedOrganization = Init.affiliatedOrganization();
	public static final Property assignedBy = Init.assignedBy();
	public static final Property assignee = Init.assignee();
	public static final Property assigneeFor = Init.assigneeFor();
	public static final Property assigns = Init.assigns();
	public static final Property cclCode = Init.cclCode();
	public static final Property conceptAssociatedWith = Init.conceptAssociatedWith();
	public static final Property confirmedOrcidId = Init.confirmedOrcidId();
	public static final Property contactInformation = Init.contactInformation();
	public static final Property contributingRole = Init.contributingRole();
	public static final Property courseCredits = Init.courseCredits();
	public static final Property dateFiled = Init.dateFiled();
	public static final Property dateIssued = Init.dateIssued();
	public static final Property dateTime = Init.dateTime();
	public static final Property dateTimeInterval = Init.dateTimeInterval();
	public static final Property dateTimePrecision = Init.dateTimePrecision();
	public static final Property dateTimeValue = Init.dateTimeValue();
	public static final Property degreeCandidacy = Init.degreeCandidacy();
	public static final Property departmentOrSchool = Init.departmentOrSchool();
	public static final Property description = Init.description();
	public static final Property distributes = Init.distributes();
	public static final Property distributesFundingFrom = Init.distributesFundingFrom();
	public static final Property eRACommonsId = Init.eRACommonsId();
	public static final Property eligibleFor = Init.eligibleFor();
	public static final Property end = Init.end();
	public static final Property entryTerm = Init.entryTerm();
	public static final Property equipmentFor = Init.equipmentFor();
	public static final Property expirationDate = Init.expirationDate();
	public static final Property facilityFor = Init.facilityFor();
	public static final Property featuredIn = Init.featuredIn();
	public static final Property features = Init.features();
	public static final Property freetextKeyword = Init.freetextKeyword();
	public static final Property fundingVehicleFor = Init.fundingVehicleFor();
	public static final Property geographicFocus = Init.geographicFocus();
	public static final Property geographicFocusOf = Init.geographicFocusOf();
	public static final Property governingAuthorityFor = Init.governingAuthorityFor();
	public static final Property grantDirectCosts = Init.grantDirectCosts();
	public static final Property grantSubcontractedThrough = Init.grantSubcontractedThrough();
	public static final Property hasAssociatedConcept = Init.hasAssociatedConcept();
	public static final Property hasCollaborator = Init.hasCollaborator();
	public static final Property hasEquipment = Init.hasEquipment();
	public static final Property hasFacility = Init.hasFacility();
	public static final Property hasFundingVehicle = Init.hasFundingVehicle();
	public static final Property hasGoverningAuthority = Init.hasGoverningAuthority();
	public static final Property hasMonetaryAmount = Init.hasMonetaryAmount();
	public static final Property hasPredecessorOrganization = Init.hasPredecessorOrganization();
	public static final Property hasPrerequisite = Init.hasPrerequisite();
	public static final Property hasProceedings = Init.hasProceedings();
	public static final Property hasPublicationVenue = Init.hasPublicationVenue();
	public static final Property hasResearchArea = Init.hasResearchArea();
	public static final Property hasSubjectArea = Init.hasSubjectArea();
	public static final Property hasSuccessorOrganization = Init.hasSuccessorOrganization();
	public static final Property hasTranslation = Init.hasTranslation();
	public static final Property hasValue = Init.hasValue();
	public static final Property hideFromDisplay = Init.hideFromDisplay();
	public static final Property hrJobTitle = Init.hrJobTitle();
	public static final Property iclCode = Init.iclCode();
	public static final Property identifier = Init.identifier();
	public static final Property informationResourceSupportedBy = Init.informationResourceSupportedBy();
	public static final Property isCorrespondingAuthor = Init.isCorrespondingAuthor();
	public static final Property licenseNumber = Init.licenseNumber();
	public static final Property localAwardId = Init.localAwardId();
	public static final Property majorField = Init.majorField();
	public static final Property middleName = Init.middleName();
	public static final Property nihmsid = Init.nihmsid();
	public static final Property offeredBy = Init.offeredBy();
	public static final Property offers = Init.offers();
	public static final Property orcidId = Init.orcidId();
	public static final Property outreachOverview = Init.outreachOverview();
	public static final Property overview = Init.overview();
	public static final Property patentNumber = Init.patentNumber();
	public static final Property placeOfPublication = Init.placeOfPublication();
	public static final Property pmcid = Init.pmcid();
	public static final Property preferredDisplayOrder = Init.preferredDisplayOrder();
	public static final Property prerequisiteFor = Init.prerequisiteFor();
	public static final Property proceedingsOf = Init.proceedingsOf();
	public static final Property providesFundingThrough = Init.providesFundingThrough();
	public static final Property publicationVenueFor = Init.publicationVenueFor();
	public static final Property publisher = Init.publisher();
	public static final Property publisherOf = Init.publisherOf();
	public static final Property rank = Init.rank();
	public static final Property relatedBy = Init.relatedBy();
	public static final Property relates = Init.relates();
	public static final Property reportId = Init.reportId();
	public static final Property reproduces = Init.reproduces();
	public static final Property researchAreaOf = Init.researchAreaOf();
	public static final Property researchOverview = Init.researchOverview();
	public static final Property researcherId = Init.researcherId();
	public static final Property reviewedIn = Init.reviewedIn();
	public static final Property roleContributesTo = Init.roleContributesTo();
	public static final Property scopusId = Init.scopusId();
	public static final Property seatingCapacity = Init.seatingCapacity();
	public static final Property sponsorAwardId = Init.sponsorAwardId();
	public static final Property sponsoredBy = Init.sponsoredBy();
	public static final Property sponsors = Init.sponsors();
	public static final Property start = Init.start();
	public static final Property subcontractsGrant = Init.subcontractsGrant();
	public static final Property subjectAreaOf = Init.subjectAreaOf();
	public static final Property supplementalInformation = Init.supplementalInformation();
	public static final Property supportedBy = Init.supportedBy();
	public static final Property supportedInformationResource = Init.supportedInformationResource();
	public static final Property supports = Init.supports();
	public static final Property teachingOverview = Init.teachingOverview();
	public static final Property termLabel = Init.termLabel();
	public static final Property termType = Init.termType();
	public static final Property totalAwardAmount = Init.totalAwardAmount();
	public static final Property translatorOf = Init.translatorOf();
	public static final Property validIn = Init.validIn();

	public static final Resource Abstract          = Init.Abstract();
	public static final Resource AcademicDegree          = Init.AcademicDegree();
	public static final Resource AcademicDepartment          = Init.AcademicDepartment();
	public static final Resource AcademicTerm          = Init.AcademicTerm();
	public static final Resource AcademicYear          = Init.AcademicYear();
	public static final Resource AdministratorRole          = Init.AdministratorRole();
	public static final Resource AdviseeRole          = Init.AdviseeRole();
	public static final Resource AdvisingProcess          = Init.AdvisingProcess();
	public static final Resource AdvisingRelationship          = Init.AdvisingRelationship();
	public static final Resource AdvisorRole          = Init.AdvisorRole();
	public static final Resource Association          = Init.Association();
	public static final Resource AttendeeRole          = Init.AttendeeRole();
	public static final Resource AttendingProcess          = Init.AttendingProcess();
	public static final Resource Authorship          = Init.Authorship();
	public static final Resource Award          = Init.Award();
	public static final Resource AwardReceipt          = Init.AwardReceipt();
	public static final Resource AwardedDegree          = Init.AwardedDegree();
	public static final Resource Blog          = Init.Blog();
	public static final Resource BlogPosting          = Init.BlogPosting();
	public static final Resource Building          = Init.Building();
	public static final Resource Campus          = Init.Campus();
	public static final Resource CaseStudy          = Init.CaseStudy();
	public static final Resource Catalog          = Init.Catalog();
	public static final Resource Center          = Init.Center();
	public static final Resource Certificate          = Init.Certificate();
	public static final Resource Certification          = Init.Certification();
	public static final Resource ClinicalOrganization          = Init.ClinicalOrganization();
	public static final Resource ClinicalRole          = Init.ClinicalRole();
	public static final Resource CoPrincipalInvestigatorRole          = Init.CoPrincipalInvestigatorRole();
	public static final Resource CollectionProcess          = Init.CollectionProcess();
	public static final Resource College          = Init.College();
	public static final Resource Committee          = Init.Committee();
	public static final Resource Company          = Init.Company();
	public static final Resource Competition          = Init.Competition();
	public static final Resource ConferencePaper          = Init.ConferencePaper();
	public static final Resource ConferencePoster          = Init.ConferencePoster();
	public static final Resource ConferenceSeries          = Init.ConferenceSeries();
	public static final Resource Consortium          = Init.Consortium();
	public static final Resource Continent          = Init.Continent();
	public static final Resource Contract          = Init.Contract();
	public static final Resource CoreLaboratory          = Init.CoreLaboratory();
	public static final Resource Country          = Init.Country();
	public static final Resource County          = Init.County();
	public static final Resource Course          = Init.Course();
	public static final Resource Credential          = Init.Credential();
	public static final Resource CurationProcess          = Init.CurationProcess();
	public static final Resource Database          = Init.Database();
	public static final Resource Dataset          = Init.Dataset();
	public static final Resource DateTimeInterval          = Init.DateTimeInterval();
	public static final Resource DateTimeValue          = Init.DateTimeValue();
	public static final Resource DateTimeValuePrecision          = Init.DateTimeValuePrecision();
	public static final Resource Department          = Init.Department();
	public static final Resource Division          = Init.Division();
	public static final Resource EditorRole          = Init.EditorRole();
	public static final Resource EditorialArticle          = Init.EditorialArticle();
	public static final Resource Editorship          = Init.Editorship();
	public static final Resource EducationalProcess          = Init.EducationalProcess();
	public static final Resource EmeritusFaculty          = Init.EmeritusFaculty();
	public static final Resource EmeritusLibrarian          = Init.EmeritusLibrarian();
	public static final Resource EmeritusProfessor          = Init.EmeritusProfessor();
	public static final Resource Equipment          = Init.Equipment();
	public static final Resource EventSeries          = Init.EventSeries();
	public static final Resource Exhibit          = Init.Exhibit();
	public static final Resource ExtensionUnit          = Init.ExtensionUnit();
	public static final Resource F1000Link          = Init.F1000Link();
	public static final Resource Facility          = Init.Facility();
	public static final Resource FacultyAdministrativePosition          = Init.FacultyAdministrativePosition();
	public static final Resource FacultyMember          = Init.FacultyMember();
	public static final Resource FacultyMentoringRelationship          = Init.FacultyMentoringRelationship();
	public static final Resource FacultyPosition          = Init.FacultyPosition();
	public static final Resource Foundation          = Init.Foundation();
	public static final Resource FundingOrganization          = Init.FundingOrganization();
	public static final Resource GeographicLocation          = Init.GeographicLocation();
	public static final Resource GeographicRegion          = Init.GeographicRegion();
	public static final Resource GeopoliticalEntity          = Init.GeopoliticalEntity();
	public static final Resource GeoreferencingProcess          = Init.GeoreferencingProcess();
	public static final Resource GovernmentAgency          = Init.GovernmentAgency();
	public static final Resource GraduateAdvisingRelationship          = Init.GraduateAdvisingRelationship();
	public static final Resource GraduateStudent          = Init.GraduateStudent();
	public static final Resource Grant          = Init.Grant();
	public static final Resource Hospital          = Init.Hospital();
	public static final Resource IdentificationProcess          = Init.IdentificationProcess();
	public static final Resource Institute          = Init.Institute();
	public static final Resource Internship          = Init.Internship();
	public static final Resource InvestigatorRole          = Init.InvestigatorRole();
	public static final Resource InvitedTalk          = Init.InvitedTalk();
	public static final Resource IssuedCredential          = Init.IssuedCredential();
	public static final Resource Laboratory          = Init.Laboratory();
	public static final Resource LeaderRole          = Init.LeaderRole();
	public static final Resource Librarian          = Init.Librarian();
	public static final Resource LibrarianPosition          = Init.LibrarianPosition();
	public static final Resource Library          = Init.Library();
	public static final Resource License          = Init.License();
	public static final Resource Licensure          = Init.Licensure();
	public static final Resource Location          = Init.Location();
	public static final Resource MeasurementProcess          = Init.MeasurementProcess();
	public static final Resource MedicalResidency          = Init.MedicalResidency();
	public static final Resource Meeting          = Init.Meeting();
	public static final Resource MemberRole          = Init.MemberRole();
	public static final Resource Museum          = Init.Museum();
	public static final Resource NewsRelease          = Init.NewsRelease();
	public static final Resource Newsletter          = Init.Newsletter();
	public static final Resource NonAcademic          = Init.NonAcademic();
	public static final Resource NonAcademicPosition          = Init.NonAcademicPosition();
	public static final Resource NonFacultyAcademic          = Init.NonFacultyAcademic();
	public static final Resource NonFacultyAcademicPosition          = Init.NonFacultyAcademicPosition();
	public static final Resource OrganizerRole          = Init.OrganizerRole();
	public static final Resource OrganizingProcess          = Init.OrganizingProcess();
	public static final Resource OutreachProviderRole          = Init.OutreachProviderRole();
	public static final Resource PeerReviewerRole          = Init.PeerReviewerRole();
	public static final Resource PopulatedPlace          = Init.PopulatedPlace();
	public static final Resource Position          = Init.Position();
	public static final Resource Postdoc          = Init.Postdoc();
	public static final Resource PostdocOrFellowAdvisingRelationship          = Init.PostdocOrFellowAdvisingRelationship();
	public static final Resource PostdocPosition          = Init.PostdocPosition();
	public static final Resource PostdoctoralTraining          = Init.PostdoctoralTraining();
	public static final Resource Presentation          = Init.Presentation();
	public static final Resource PresenterRole          = Init.PresenterRole();
	public static final Resource PresentingProcess          = Init.PresentingProcess();
	public static final Resource PrimaryPosition          = Init.PrimaryPosition();
	public static final Resource PrincipalInvestigatorRole          = Init.PrincipalInvestigatorRole();
	public static final Resource PrivateCompany          = Init.PrivateCompany();
	public static final Resource Program          = Init.Program();
	public static final Resource Project          = Init.Project();
	public static final Resource Publisher          = Init.Publisher();
	public static final Resource Relationship          = Init.Relationship();
	public static final Resource ResearchOrganization          = Init.ResearchOrganization();
	public static final Resource ResearchProposal          = Init.ResearchProposal();
	public static final Resource ResearcherRole          = Init.ResearcherRole();
	public static final Resource Review          = Init.Review();
	public static final Resource ReviewerRole          = Init.ReviewerRole();
	public static final Resource Room          = Init.Room();
	public static final Resource School          = Init.School();
	public static final Resource Score          = Init.Score();
	public static final Resource Screenplay          = Init.Screenplay();
	public static final Resource SeminarSeries          = Init.SeminarSeries();
	public static final Resource ServiceProvidingLaboratory          = Init.ServiceProvidingLaboratory();
	public static final Resource Speech          = Init.Speech();
	public static final Resource StateOrProvince          = Init.StateOrProvince();
	public static final Resource Student          = Init.Student();
	public static final Resource StudentOrganization          = Init.StudentOrganization();
	public static final Resource SubnationalRegion          = Init.SubnationalRegion();
	public static final Resource TeacherRole          = Init.TeacherRole();
	public static final Resource Team          = Init.Team();
	public static final Resource Translation          = Init.Translation();
	public static final Resource UndergraduateAdvisingRelationship          = Init.UndergraduateAdvisingRelationship();
	public static final Resource UndergraduateStudent          = Init.UndergraduateStudent();
	public static final Resource University          = Init.University();
	public static final Resource Video          = Init.Video();
	public static final Resource WorkingPaper          = Init.WorkingPaper();
	public static final Resource WorkshopSeries          = Init.WorkshopSeries();


	public static final Property domain         = Init.domain();


	public static class Init {
		public static Property  abbreviation()        { return property( "abbreviation"); }
		public static Property  affiliatedOrganization()        { return property( "affiliatedOrganization"); }
		public static Property  assignedBy()        { return property( "assignedBy"); }
		public static Property  assignee()        { return property( "assignee"); }
		public static Property  assigneeFor()        { return property( "assigneeFor"); }
		public static Property  assigns()        { return property( "assigns"); }
		public static Property  cclCode()        { return property( "cclCode"); }
		public static Property  conceptAssociatedWith()        { return property( "conceptAssociatedWith"); }
		public static Property  confirmedOrcidId()        { return property( "confirmedOrcidId"); }
		public static Property  contactInformation()        { return property( "contactInformation"); }
		public static Property  contributingRole()        { return property( "contributingRole"); }
		public static Property  courseCredits()        { return property( "courseCredits"); }
		public static Property  dateFiled()        { return property( "dateFiled"); }
		public static Property  dateIssued()        { return property( "dateIssued"); }
		public static Property  dateTime()        { return property( "dateTime"); }
		public static Property  dateTimeInterval()        { return property( "dateTimeInterval"); }
		public static Property  dateTimePrecision()        { return property( "dateTimePrecision"); }
		public static Property  dateTimeValue()        { return property( "dateTimeValue"); }
		public static Property  degreeCandidacy()        { return property( "degreeCandidacy"); }
		public static Property  departmentOrSchool()        { return property( "departmentOrSchool"); }
		public static Property  description()        { return property( "description"); }
		public static Property  distributes()        { return property( "distributes"); }
		public static Property  distributesFundingFrom()        { return property( "distributesFundingFrom"); }
		public static Property  eRACommonsId()        { return property( "eRACommonsId"); }
		public static Property  eligibleFor()        { return property( "eligibleFor"); }
		public static Property  end()        { return property( "end"); }
		public static Property  entryTerm()        { return property( "entryTerm"); }
		public static Property  equipmentFor()        { return property( "equipmentFor"); }
		public static Property  expirationDate()        { return property( "expirationDate"); }
		public static Property  facilityFor()        { return property( "facilityFor"); }
		public static Property  featuredIn()        { return property( "featuredIn"); }
		public static Property  features()        { return property( "features"); }
		public static Property  freetextKeyword()        { return property( "freetextKeyword"); }
		public static Property  fundingVehicleFor()        { return property( "fundingVehicleFor"); }
		public static Property  geographicFocus()        { return property( "geographicFocus"); }
		public static Property  geographicFocusOf()        { return property( "geographicFocusOf"); }
		public static Property  governingAuthorityFor()        { return property( "governingAuthorityFor"); }
		public static Property  grantDirectCosts()        { return property( "grantDirectCosts"); }
		public static Property  grantSubcontractedThrough()        { return property( "grantSubcontractedThrough"); }
		public static Property  hasAssociatedConcept()        { return property( "hasAssociatedConcept"); }
		public static Property  hasCollaborator()        { return property( "hasCollaborator"); }
		public static Property  hasEquipment()        { return property( "hasEquipment"); }
		public static Property  hasFacility()        { return property( "hasFacility"); }
		public static Property  hasFundingVehicle()        { return property( "hasFundingVehicle"); }
		public static Property  hasGoverningAuthority()        { return property( "hasGoverningAuthority"); }
		public static Property  hasMonetaryAmount()        { return property( "hasMonetaryAmount"); }
		public static Property  hasPredecessorOrganization()        { return property( "hasPredecessorOrganization"); }
		public static Property  hasPrerequisite()        { return property( "hasPrerequisite"); }
		public static Property  hasProceedings()        { return property( "hasProceedings"); }
		public static Property  hasPublicationVenue()        { return property( "hasPublicationVenue"); }
		public static Property  hasResearchArea()        { return property( "hasResearchArea"); }
		public static Property  hasSubjectArea()        { return property( "hasSubjectArea"); }
		public static Property  hasSuccessorOrganization()        { return property( "hasSuccessorOrganization"); }
		public static Property  hasTranslation()        { return property( "hasTranslation"); }
		public static Property  hasValue()        { return property( "hasValue"); }
		public static Property  hideFromDisplay()        { return property( "hideFromDisplay"); }
		public static Property  hrJobTitle()        { return property( "hrJobTitle"); }
		public static Property  iclCode()        { return property( "iclCode"); }
		public static Property  identifier()        { return property( "identifier"); }
		public static Property  informationResourceSupportedBy()        { return property( "informationResourceSupportedBy"); }
		public static Property  isCorrespondingAuthor()        { return property( "isCorrespondingAuthor"); }
		public static Property  licenseNumber()        { return property( "licenseNumber"); }
		public static Property  localAwardId()        { return property( "localAwardId"); }
		public static Property  majorField()        { return property( "majorField"); }
		public static Property  middleName()        { return property( "middleName"); }
		public static Property  nihmsid()        { return property( "nihmsid"); }
		public static Property  offeredBy()        { return property( "offeredBy"); }
		public static Property  offers()        { return property( "offers"); }
		public static Property  orcidId()        { return property( "orcidId"); }
		public static Property  outreachOverview()        { return property( "outreachOverview"); }
		public static Property  overview()        { return property( "overview"); }
		public static Property  patentNumber()        { return property( "patentNumber"); }
		public static Property  placeOfPublication()        { return property( "placeOfPublication"); }
		public static Property  pmcid()        { return property( "pmcid"); }
		public static Property  preferredDisplayOrder()        { return property( "preferredDisplayOrder"); }
		public static Property  prerequisiteFor()        { return property( "prerequisiteFor"); }
		public static Property  proceedingsOf()        { return property( "proceedingsOf"); }
		public static Property  providesFundingThrough()        { return property( "providesFundingThrough"); }
		public static Property  publicationVenueFor()        { return property( "publicationVenueFor"); }
		public static Property  publisher()        { return property( "publisher"); }
		public static Property  publisherOf()        { return property( "publisherOf"); }
		public static Property  rank()        { return property( "rank"); }
		public static Property  relatedBy()        { return property( "relatedBy"); }
		public static Property  relates()        { return property( "relates"); }
		public static Property  reportId()        { return property( "reportId"); }
		public static Property  reproduces()        { return property( "reproduces"); }
		public static Property  researchAreaOf()        { return property( "researchAreaOf"); }
		public static Property  researchOverview()        { return property( "researchOverview"); }
		public static Property  researcherId()        { return property( "researcherId"); }
		public static Property  reviewedIn()        { return property( "reviewedIn"); }
		public static Property  roleContributesTo()        { return property( "roleContributesTo"); }
		public static Property  scopusId()        { return property( "scopusId"); }
		public static Property  seatingCapacity()        { return property( "seatingCapacity"); }
		public static Property  sponsorAwardId()        { return property( "sponsorAwardId"); }
		public static Property  sponsoredBy()        { return property( "sponsoredBy"); }
		public static Property  sponsors()        { return property( "sponsors"); }
		public static Property  start()        { return property( "start"); }
		public static Property  subcontractsGrant()        { return property( "subcontractsGrant"); }
		public static Property  subjectAreaOf()        { return property( "subjectAreaOf"); }
		public static Property  supplementalInformation()        { return property( "supplementalInformation"); }
		public static Property  supportedBy()        { return property( "supportedBy"); }
		public static Property  supportedInformationResource()        { return property( "supportedInformationResource"); }
		public static Property  supports()        { return property( "supports"); }
		public static Property  teachingOverview()        { return property( "teachingOverview"); }
		public static Property  termLabel()        { return property( "termLabel"); }
		public static Property  termType()        { return property( "termType"); }
		public static Property  totalAwardAmount()        { return property( "totalAwardAmount"); }
		public static Property  translatorOf()        { return property( "translatorOf"); }
		public static Property  validIn()        { return property( "validIn"); }

		public static Resource Abstract()              { return resource( "Abstract" ); }
		public static Resource AcademicDegree()              { return resource( "AcademicDegree" ); }
		public static Resource AcademicDepartment()              { return resource( "AcademicDepartment" ); }
		public static Resource AcademicTerm()              { return resource( "AcademicTerm" ); }
		public static Resource AcademicYear()              { return resource( "AcademicYear" ); }
		public static Resource AdministratorRole()              { return resource( "AdministratorRole" ); }
		public static Resource AdviseeRole()              { return resource( "AdviseeRole" ); }
		public static Resource AdvisingProcess()              { return resource( "AdvisingProcess" ); }
		public static Resource AdvisingRelationship()              { return resource( "AdvisingRelationship" ); }
		public static Resource AdvisorRole()              { return resource( "AdvisorRole" ); }
		public static Resource Association()              { return resource( "Association" ); }
		public static Resource AttendeeRole()              { return resource( "AttendeeRole" ); }
		public static Resource AttendingProcess()              { return resource( "AttendingProcess" ); }
		public static Resource Authorship()              { return resource( "Authorship" ); }
		public static Resource Award()              { return resource( "Award" ); }
		public static Resource AwardReceipt()              { return resource( "AwardReceipt" ); }
		public static Resource AwardedDegree()              { return resource( "AwardedDegree" ); }
		public static Resource Blog()              { return resource( "Blog" ); }
		public static Resource BlogPosting()              { return resource( "BlogPosting" ); }
		public static Resource Building()              { return resource( "Building" ); }
		public static Resource Campus()              { return resource( "Campus" ); }
		public static Resource CaseStudy()              { return resource( "CaseStudy" ); }
		public static Resource Catalog()              { return resource( "Catalog" ); }
		public static Resource Center()              { return resource( "Center" ); }
		public static Resource Certificate()              { return resource( "Certificate" ); }
		public static Resource Certification()              { return resource( "Certification" ); }
		public static Resource ClinicalOrganization()              { return resource( "ClinicalOrganization" ); }
		public static Resource ClinicalRole()              { return resource( "ClinicalRole" ); }
		public static Resource CoPrincipalInvestigatorRole()              { return resource( "CoPrincipalInvestigatorRole" ); }
		public static Resource CollectionProcess()              { return resource( "CollectionProcess" ); }
		public static Resource College()              { return resource( "College" ); }
		public static Resource Committee()              { return resource( "Committee" ); }
		public static Resource Company()              { return resource( "Company" ); }
		public static Resource Competition()              { return resource( "Competition" ); }
		public static Resource ConferencePaper()              { return resource( "ConferencePaper" ); }
		public static Resource ConferencePoster()              { return resource( "ConferencePoster" ); }
		public static Resource ConferenceSeries()              { return resource( "ConferenceSeries" ); }
		public static Resource Consortium()              { return resource( "Consortium" ); }
		public static Resource Continent()              { return resource( "Continent" ); }
		public static Resource Contract()              { return resource( "Contract" ); }
		public static Resource CoreLaboratory()              { return resource( "CoreLaboratory" ); }
		public static Resource Country()              { return resource( "Country" ); }
		public static Resource County()              { return resource( "County" ); }
		public static Resource Course()              { return resource( "Course" ); }
		public static Resource Credential()              { return resource( "Credential" ); }
		public static Resource CurationProcess()              { return resource( "CurationProcess" ); }
		public static Resource Database()              { return resource( "Database" ); }
		public static Resource Dataset()              { return resource( "Dataset" ); }
		public static Resource DateTimeInterval()              { return resource( "DateTimeInterval" ); }
		public static Resource DateTimeValue()              { return resource( "DateTimeValue" ); }
		public static Resource DateTimeValuePrecision()              { return resource( "DateTimeValuePrecision" ); }
		public static Resource Department()              { return resource( "Department" ); }
		public static Resource Division()              { return resource( "Division" ); }
		public static Resource EditorRole()              { return resource( "EditorRole" ); }
		public static Resource EditorialArticle()              { return resource( "EditorialArticle" ); }
		public static Resource Editorship()              { return resource( "Editorship" ); }
		public static Resource EducationalProcess()              { return resource( "EducationalProcess" ); }
		public static Resource EmeritusFaculty()              { return resource( "EmeritusFaculty" ); }
		public static Resource EmeritusLibrarian()              { return resource( "EmeritusLibrarian" ); }
		public static Resource EmeritusProfessor()              { return resource( "EmeritusProfessor" ); }
		public static Resource Equipment()              { return resource( "Equipment" ); }
		public static Resource EventSeries()              { return resource( "EventSeries" ); }
		public static Resource Exhibit()              { return resource( "Exhibit" ); }
		public static Resource ExtensionUnit()              { return resource( "ExtensionUnit" ); }
		public static Resource F1000Link()              { return resource( "F1000Link" ); }
		public static Resource Facility()              { return resource( "Facility" ); }
		public static Resource FacultyAdministrativePosition()              { return resource( "FacultyAdministrativePosition" ); }
		public static Resource FacultyMember()              { return resource( "FacultyMember" ); }
		public static Resource FacultyMentoringRelationship()              { return resource( "FacultyMentoringRelationship" ); }
		public static Resource FacultyPosition()              { return resource( "FacultyPosition" ); }
		public static Resource Foundation()              { return resource( "Foundation" ); }
		public static Resource FundingOrganization()              { return resource( "FundingOrganization" ); }
		public static Resource GeographicLocation()              { return resource( "GeographicLocation" ); }
		public static Resource GeographicRegion()              { return resource( "GeographicRegion" ); }
		public static Resource GeopoliticalEntity()              { return resource( "GeopoliticalEntity" ); }
		public static Resource GeoreferencingProcess()              { return resource( "GeoreferencingProcess" ); }
		public static Resource GovernmentAgency()              { return resource( "GovernmentAgency" ); }
		public static Resource GraduateAdvisingRelationship()              { return resource( "GraduateAdvisingRelationship" ); }
		public static Resource GraduateStudent()              { return resource( "GraduateStudent" ); }
		public static Resource Grant()              { return resource( "Grant" ); }
		public static Resource Hospital()              { return resource( "Hospital" ); }
		public static Resource IdentificationProcess()              { return resource( "IdentificationProcess" ); }
		public static Resource Institute()              { return resource( "Institute" ); }
		public static Resource Internship()              { return resource( "Internship" ); }
		public static Resource InvestigatorRole()              { return resource( "InvestigatorRole" ); }
		public static Resource InvitedTalk()              { return resource( "InvitedTalk" ); }
		public static Resource IssuedCredential()              { return resource( "IssuedCredential" ); }
		public static Resource Laboratory()              { return resource( "Laboratory" ); }
		public static Resource LeaderRole()              { return resource( "LeaderRole" ); }
		public static Resource Librarian()              { return resource( "Librarian" ); }
		public static Resource LibrarianPosition()              { return resource( "LibrarianPosition" ); }
		public static Resource Library()              { return resource( "Library" ); }
		public static Resource License()              { return resource( "License" ); }
		public static Resource Licensure()              { return resource( "Licensure" ); }
		public static Resource Location()              { return resource( "Location" ); }
		public static Resource MeasurementProcess()              { return resource( "MeasurementProcess" ); }
		public static Resource MedicalResidency()              { return resource( "MedicalResidency" ); }
		public static Resource Meeting()              { return resource( "Meeting" ); }
		public static Resource MemberRole()              { return resource( "MemberRole" ); }
		public static Resource Museum()              { return resource( "Museum" ); }
		public static Resource NewsRelease()              { return resource( "NewsRelease" ); }
		public static Resource Newsletter()              { return resource( "Newsletter" ); }
		public static Resource NonAcademic()              { return resource( "NonAcademic" ); }
		public static Resource NonAcademicPosition()              { return resource( "NonAcademicPosition" ); }
		public static Resource NonFacultyAcademic()              { return resource( "NonFacultyAcademic" ); }
		public static Resource NonFacultyAcademicPosition()              { return resource( "NonFacultyAcademicPosition" ); }
		public static Resource OrganizerRole()              { return resource( "OrganizerRole" ); }
		public static Resource OrganizingProcess()              { return resource( "OrganizingProcess" ); }
		public static Resource OutreachProviderRole()              { return resource( "OutreachProviderRole" ); }
		public static Resource PeerReviewerRole()              { return resource( "PeerReviewerRole" ); }
		public static Resource PopulatedPlace()              { return resource( "PopulatedPlace" ); }
		public static Resource Position()              { return resource( "Position" ); }
		public static Resource Postdoc()              { return resource( "Postdoc" ); }
		public static Resource PostdocOrFellowAdvisingRelationship()              { return resource( "PostdocOrFellowAdvisingRelationship" ); }
		public static Resource PostdocPosition()              { return resource( "PostdocPosition" ); }
		public static Resource PostdoctoralTraining()              { return resource( "PostdoctoralTraining" ); }
		public static Resource Presentation()              { return resource( "Presentation" ); }
		public static Resource PresenterRole()              { return resource( "PresenterRole" ); }
		public static Resource PresentingProcess()              { return resource( "PresentingProcess" ); }
		public static Resource PrimaryPosition()              { return resource( "PrimaryPosition" ); }
		public static Resource PrincipalInvestigatorRole()              { return resource( "PrincipalInvestigatorRole" ); }
		public static Resource PrivateCompany()              { return resource( "PrivateCompany" ); }
		public static Resource Program()              { return resource( "Program" ); }
		public static Resource Project()              { return resource( "Project" ); }
		public static Resource Publisher()              { return resource( "Publisher" ); }
		public static Resource Relationship()              { return resource( "Relationship" ); }
		public static Resource ResearchOrganization()              { return resource( "ResearchOrganization" ); }
		public static Resource ResearchProposal()              { return resource( "ResearchProposal" ); }
		public static Resource ResearcherRole()              { return resource( "ResearcherRole" ); }
		public static Resource Review()              { return resource( "Review" ); }
		public static Resource ReviewerRole()              { return resource( "ReviewerRole" ); }
		public static Resource Room()              { return resource( "Room" ); }
		public static Resource School()              { return resource( "School" ); }
		public static Resource Score()              { return resource( "Score" ); }
		public static Resource Screenplay()              { return resource( "Screenplay" ); }
		public static Resource SeminarSeries()              { return resource( "SeminarSeries" ); }
		public static Resource ServiceProvidingLaboratory()              { return resource( "ServiceProvidingLaboratory" ); }
		public static Resource Speech()              { return resource( "Speech" ); }
		public static Resource StateOrProvince()              { return resource( "StateOrProvince" ); }
		public static Resource Student()              { return resource( "Student" ); }
		public static Resource StudentOrganization()              { return resource( "StudentOrganization" ); }
		public static Resource SubnationalRegion()              { return resource( "SubnationalRegion" ); }
		public static Resource TeacherRole()              { return resource( "TeacherRole" ); }
		public static Resource Team()              { return resource( "Team" ); }
		public static Resource Translation()              { return resource( "Translation" ); }
		public static Resource UndergraduateAdvisingRelationship()              { return resource( "UndergraduateAdvisingRelationship" ); }
		public static Resource UndergraduateStudent()              { return resource( "UndergraduateStudent" ); }
		public static Resource University()              { return resource( "University" ); }
		public static Resource Video()              { return resource( "Video" ); }
		public static Resource WorkingPaper()              { return resource( "WorkingPaper" ); }
		public static Resource WorkshopSeries()              { return resource( "WorkshopSeries" ); }
		public static Property domain()         { return property( "domain"); }

	}


}
