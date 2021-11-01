package ca.uqam.tool.vivoproxy.pattern.command.receiver.util;

public class EditKeyForPosition {
    private String subjectUri;
    private String predicateUri;
    private String domainUri;
    private String rangeUri;
    /**
     * @return the subjectUri
     */
    public String getSubjectUri() {
        return subjectUri;
    }
    /**
     * @param subjectUri the subjectUri to set
     */
    public void setSubjectUri(String subjectUri) {
        this.subjectUri = subjectUri;
    }
    /**
     * @return the predicateUri
     */
    public String getPredicateUri() {
        return predicateUri;
    }
    /**
     * @param predicateUri the predicateUri to set
     */
    public void setPredicateUri(String predicateUri) {
        this.predicateUri = predicateUri;
    }
    /**
     * @return the domainUri
     */
    public String getDomainUri() {
        return domainUri;
    }
    /**
     * @param domainUri the domainUri to set
     */
    public void setDomainUri(String domainUri) {
        this.domainUri = domainUri;
    }
    /**
     * @return the rangeUri
     */
    public String getRangeUri() {
        return rangeUri;
    }
    /**
     * @param rangeUri the rangeUri to set
     */
    public void setRangeUri(String rangeUri) {
        this.rangeUri = rangeUri;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "EditKeyForPosition [" + (subjectUri != null ? "subjectUri=" + subjectUri + ", " : "")
                + (predicateUri != null ? "predicateUri=" + predicateUri + ", " : "")
                + (domainUri != null ? "domainUri=" + domainUri + ", " : "")
                + (rangeUri != null ? "rangeUri=" + rangeUri : "") + "]";
    }


}
