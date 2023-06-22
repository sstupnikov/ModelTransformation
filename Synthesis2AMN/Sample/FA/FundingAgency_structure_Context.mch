MACHINE FundingAgency_structure_Context

SETS
	AVAL;
	OID;
	Researcher_research_area_Enum = {computer_science, mathematics, biology, chemistry};
	Specialist_research_field_Enum = {comp_sci, math, bio, chem};
	Specialist_degree_Enum = {PhD, DrSc, MSc}

ABSTRACT_CONSTANTS
	Obj,
	self,
	extp_Teacher,
	ext_Teacher,
	extp_Student,
	ext_Student,
	extp_Proposal,
	ext_Proposal,
	extp_Review,
	ext_Review,
	extp_Person,
	ext_Person,
	extp_Researcher,
	ext_Researcher,
	extp_Expert,
	ext_Expert,
	extp_Secretary,
	ext_Secretary,
	extp_Submission,
	ext_Submission,
	extp_Evaluation,
	ext_Evaluation,
	extp_Employee,
	ext_Employee,
	extp_Specialist,
	ext_Specialist,
	extp_Dispatcher,
	ext_Dispatcher

PROPERTIES
	Obj : POW(AVAL) &
	self : (Obj >->> OID) &
	extp_Teacher : POW(Obj) &
	ext_Teacher : POW(Obj) &
	extp_Teacher <: ext_Teacher &
	extp_Student : POW(Obj) &
	ext_Student : POW(Obj) &
	extp_Student <: ext_Student &
	extp_Proposal : POW(Obj) &
	ext_Proposal : POW(Obj) &
	extp_Proposal <: ext_Proposal &
	extp_Review : POW(Obj) &
	ext_Review : POW(Obj) &
	extp_Review <: ext_Review &
	extp_Person : POW(Obj) &
	ext_Person : POW(Obj) &
	extp_Person <: ext_Person &
	extp_Researcher : POW(Obj) &
	ext_Researcher : POW(Obj) &
	extp_Researcher <: ext_Researcher &
	extp_Expert : POW(Obj) &
	ext_Expert : POW(Obj) &
	extp_Expert <: ext_Expert &
	extp_Secretary : POW(Obj) &
	ext_Secretary : POW(Obj) &
	extp_Secretary <: ext_Secretary &
	extp_Submission : POW(Obj) &
	ext_Submission : POW(Obj) &
	extp_Submission <: ext_Submission &
	extp_Evaluation : POW(Obj) &
	ext_Evaluation : POW(Obj) &
	extp_Evaluation <: ext_Evaluation &
	extp_Employee : POW(Obj) &
	ext_Employee : POW(Obj) &
	extp_Employee <: ext_Employee &
	extp_Specialist : POW(Obj) &
	ext_Specialist : POW(Obj) &
	extp_Specialist <: ext_Specialist &
	extp_Dispatcher : POW(Obj) &
	ext_Dispatcher : POW(Obj) &
	extp_Dispatcher <: ext_Dispatcher &
	(extp_Teacher /\ extp_Student) = {} &
	(extp_Teacher /\ extp_Proposal) = {} &
	(extp_Teacher /\ extp_Review) = {} &
	(extp_Teacher /\ extp_Person) = {} &
	(extp_Teacher /\ extp_Researcher) = {} &
	(extp_Teacher /\ extp_Expert) = {} &
	(extp_Teacher /\ extp_Secretary) = {} &
	(extp_Teacher /\ extp_Submission) = {} &
	(extp_Teacher /\ extp_Evaluation) = {} &
	(extp_Teacher /\ extp_Employee) = {} &
	(extp_Teacher /\ extp_Specialist) = {} &
	(extp_Teacher /\ extp_Dispatcher) = {} &
	(extp_Student /\ extp_Proposal) = {} &
	(extp_Student /\ extp_Review) = {} &
	(extp_Student /\ extp_Person) = {} &
	(extp_Student /\ extp_Researcher) = {} &
	(extp_Student /\ extp_Expert) = {} &
	(extp_Student /\ extp_Secretary) = {} &
	(extp_Student /\ extp_Submission) = {} &
	(extp_Student /\ extp_Evaluation) = {} &
	(extp_Student /\ extp_Employee) = {} &
	(extp_Student /\ extp_Specialist) = {} &
	(extp_Student /\ extp_Dispatcher) = {} &
	(extp_Proposal /\ extp_Review) = {} &
	(extp_Proposal /\ extp_Person) = {} &
	(extp_Proposal /\ extp_Researcher) = {} &
	(extp_Proposal /\ extp_Expert) = {} &
	(extp_Proposal /\ extp_Secretary) = {} &
	(extp_Proposal /\ extp_Submission) = {} &
	(extp_Proposal /\ extp_Evaluation) = {} &
	(extp_Proposal /\ extp_Employee) = {} &
	(extp_Proposal /\ extp_Specialist) = {} &
	(extp_Proposal /\ extp_Dispatcher) = {} &
	(extp_Review /\ extp_Person) = {} &
	(extp_Review /\ extp_Researcher) = {} &
	(extp_Review /\ extp_Expert) = {} &
	(extp_Review /\ extp_Secretary) = {} &
	(extp_Review /\ extp_Submission) = {} &
	(extp_Review /\ extp_Evaluation) = {} &
	(extp_Review /\ extp_Employee) = {} &
	(extp_Review /\ extp_Specialist) = {} &
	(extp_Review /\ extp_Dispatcher) = {} &
	(extp_Person /\ extp_Researcher) = {} &
	(extp_Person /\ extp_Expert) = {} &
	(extp_Person /\ extp_Secretary) = {} &
	(extp_Person /\ extp_Submission) = {} &
	(extp_Person /\ extp_Evaluation) = {} &
	(extp_Person /\ extp_Employee) = {} &
	(extp_Person /\ extp_Specialist) = {} &
	(extp_Person /\ extp_Dispatcher) = {} &
	(extp_Researcher /\ extp_Expert) = {} &
	(extp_Researcher /\ extp_Secretary) = {} &
	(extp_Researcher /\ extp_Submission) = {} &
	(extp_Researcher /\ extp_Evaluation) = {} &
	(extp_Researcher /\ extp_Employee) = {} &
	(extp_Researcher /\ extp_Specialist) = {} &
	(extp_Researcher /\ extp_Dispatcher) = {} &
	(extp_Expert /\ extp_Secretary) = {} &
	(extp_Expert /\ extp_Submission) = {} &
	(extp_Expert /\ extp_Evaluation) = {} &
	(extp_Expert /\ extp_Employee) = {} &
	(extp_Expert /\ extp_Specialist) = {} &
	(extp_Expert /\ extp_Dispatcher) = {} &
	(extp_Secretary /\ extp_Submission) = {} &
	(extp_Secretary /\ extp_Evaluation) = {} &
	(extp_Secretary /\ extp_Employee) = {} &
	(extp_Secretary /\ extp_Specialist) = {} &
	(extp_Secretary /\ extp_Dispatcher) = {} &
	(extp_Submission /\ extp_Evaluation) = {} &
	(extp_Submission /\ extp_Employee) = {} &
	(extp_Submission /\ extp_Specialist) = {} &
	(extp_Submission /\ extp_Dispatcher) = {} &
	(extp_Evaluation /\ extp_Employee) = {} &
	(extp_Evaluation /\ extp_Specialist) = {} &
	(extp_Evaluation /\ extp_Dispatcher) = {} &
	(extp_Employee /\ extp_Specialist) = {} &
	(extp_Employee /\ extp_Dispatcher) = {} &
	(extp_Specialist /\ extp_Dispatcher) = {} &
	ext_Teacher <<: ext_Specialist &
	ext_Researcher <<: ext_Person &
	ext_Expert <<: ext_Researcher &
	ext_Specialist <<: ext_Employee

END