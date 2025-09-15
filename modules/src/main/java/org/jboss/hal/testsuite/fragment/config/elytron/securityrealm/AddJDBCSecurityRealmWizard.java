package org.jboss.hal.testsuite.fragment.config.elytron.securityrealm;

public class AddJDBCSecurityRealmWizard extends AbstractAddSecurityRealmWizard<AddJDBCSecurityRealmWizard> {

    public AddJDBCSecurityRealmWizard principalQuerySQL(String principalQuerySQL) {
        getEditor().text("principal-query-sql", principalQuerySQL);
        return this;
    }

    public AddJDBCSecurityRealmWizard principalQueryDatasource(String principalQueryDatasource) {
        getEditor().text("principal-query-datasource", principalQueryDatasource);
        return this;
    }
}
