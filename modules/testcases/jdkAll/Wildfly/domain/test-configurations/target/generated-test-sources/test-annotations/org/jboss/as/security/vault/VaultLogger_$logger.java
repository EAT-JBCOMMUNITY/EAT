package org.jboss.as.security.vault;

import java.util.Locale;
import java.io.Serializable;
import javax.annotation.Generated;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.security.vault.SecurityVaultException;
import java.lang.SecurityException;
import java.lang.String;
import org.jboss.logging.Logger;
import java.lang.Exception;
import java.lang.RuntimeException;
import org.jboss.logging.BasicLogger;
import java.lang.Throwable;
import java.util.Arrays;

/**
 * Warning this class consists of generated code.
 */
@Generated(value = "org.jboss.logging.processor.generator.model.MessageLoggerImplementor", date = "2022-10-02T14:24:32+0300")
public class VaultLogger_$logger extends DelegatingBasicLogger implements VaultLogger, BasicLogger, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FQCN = VaultLogger_$logger.class.getName();
    public VaultLogger_$logger(final Logger log) {
        super(log);
    }
    private static final Locale LOCALE = Locale.ROOT;
    protected Locale getLoggingLocale() {
        return LOCALE;
    }
    protected String runtimeException$str() {
        return "WFLYSEC0007: Runtime Exception:";
    }
    @Override
    public final RuntimeException runtimeException(final Throwable e) {
        final RuntimeException result = new RuntimeException(String.format(getLoggingLocale(), runtimeException$str()), e);
        _copyStackTraceMinusOne(result);
        return result;
    }
    private static void _copyStackTraceMinusOne(final Throwable e) {
        final StackTraceElement[] st = e.getStackTrace();
        e.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
    }
    protected String securityException$str() {
        return "WFLYSEC0015: Security Exception";
    }
    @Override
    public final SecurityException securityException(final Throwable t) {
        final SecurityException result = new SecurityException(String.format(getLoggingLocale(), securityException$str()), t);
        _copyStackTraceMinusOne(result);
        return result;
    }
    protected String vaultReaderException$str() {
        return "WFLYSEC0017: Vault Reader Exception:";
    }
    @Override
    public final Exception vaultReaderException(final Throwable t) {
        final Exception result = new Exception(String.format(getLoggingLocale(), vaultReaderException$str()), t);
        _copyStackTraceMinusOne(result);
        return result;
    }
    protected String vaultNotInitializedException$str() {
        return "WFLYSEC0026: Vault is not initialized";
    }
    @Override
    public final SecurityException vaultNotInitializedException() {
        final SecurityException result = new SecurityException(String.format(getLoggingLocale(), vaultNotInitializedException$str()));
        _copyStackTraceMinusOne(result);
        return result;
    }
    protected String keyStoreDoesnotExistWithExample$str() {
        return "WFLYSEC0037: Keystore '%s' doesn't exist.\nkeystore could be created: keytool -genseckey -alias Vault -storetype jceks -keyalg AES -keysize 128 -storepass secretsecret -keypass secretsecret -keystore %s";
    }
    @Override
    public final Exception keyStoreDoesnotExistWithExample(final String keystoreURL, final String keystoreURLExample) {
        final Exception result = new Exception(String.format(getLoggingLocale(), keyStoreDoesnotExistWithExample$str(), keystoreURL, keystoreURLExample));
        _copyStackTraceMinusOne(result);
        return result;
    }
    protected String keyStoreNotWritable$str() {
        return "WFLYSEC0038: Keystore [%s] is not writable or not a file.";
    }
    @Override
    public final Exception keyStoreNotWritable(final String keystoreURL) {
        final Exception result = new Exception(String.format(getLoggingLocale(), keyStoreNotWritable$str(), keystoreURL));
        _copyStackTraceMinusOne(result);
        return result;
    }
    protected String keyStorePasswordNotSpecified$str() {
        return "WFLYSEC0039: Keystore password has to be specified.";
    }
    @Override
    public final Exception keyStorePasswordNotSpecified() {
        final Exception result = new Exception(String.format(getLoggingLocale(), keyStorePasswordNotSpecified$str()));
        _copyStackTraceMinusOne(result);
        return result;
    }
    protected String encryptionDirectoryDoesNotExist$str() {
        return "WFLYSEC0041: Encryption directory is not a directory or doesn't exist. (%s)";
    }
    @Override
    public final Exception encryptionDirectoryDoesNotExist(final String directory) {
        final Exception result = new Exception(String.format(getLoggingLocale(), encryptionDirectoryDoesNotExist$str(), directory));
        _copyStackTraceMinusOne(result);
        return result;
    }
    protected String cannotCreateEncryptionDirectory$str() {
        return "WFLYSEC0042: Cannot create encryption directory %s";
    }
    @Override
    public final Exception cannotCreateEncryptionDirectory(final String directory) {
        final Exception result = new Exception(String.format(getLoggingLocale(), cannotCreateEncryptionDirectory$str(), directory));
        _copyStackTraceMinusOne(result);
        return result;
    }
    protected String iterationCountOutOfRange$str() {
        return "WFLYSEC0043: Iteration count has to be within 1 - 2147483647, but it is %s.";
    }
    @Override
    public final Exception iterationCountOutOfRange(final String iteration) {
        final Exception result = new Exception(String.format(getLoggingLocale(), iterationCountOutOfRange$str(), iteration));
        _copyStackTraceMinusOne(result);
        return result;
    }
    protected String saltWrongLength$str() {
        return "WFLYSEC0044: Salt has to be exactly 8 characters long.";
    }
    @Override
    public final Exception saltWrongLength() {
        final Exception result = new Exception(String.format(getLoggingLocale(), saltWrongLength$str()));
        _copyStackTraceMinusOne(result);
        return result;
    }
    protected String securityVaultException$str() {
        return "WFLYSEC0045: Exception encountered:";
    }
    @Override
    public final Exception securityVaultException(final SecurityVaultException cause) {
        final Exception result = new Exception(String.format(getLoggingLocale(), securityVaultException$str()), cause);
        _copyStackTraceMinusOne(result);
        return result;
    }
    protected String vaultAliasNotSpecified$str() {
        return "WFLYSEC0046: Vault alias has to be specified.";
    }
    @Override
    public final Exception vaultAliasNotSpecified() {
        final Exception result = new Exception(String.format(getLoggingLocale(), vaultAliasNotSpecified$str()));
        _copyStackTraceMinusOne(result);
        return result;
    }
    protected String vaultAttributeCreateDisplay$str() {
        return "WFLYSEC0047: Secured attribute value has been stored in Vault.\nPlease make note of the following:\n********************************************\nVault Block:%s\nAttribute Name:%s\nConfiguration should be done as follows:\n%s\n********************************************";
    }
    @Override
    public final String vaultAttributeCreateDisplay(final String VaultBlock, final String attributeName, final String configurationString) {
        return String.format(getLoggingLocale(), vaultAttributeCreateDisplay$str(), VaultBlock, attributeName, configurationString);
    }
    protected String vaultConfigurationTitle$str() {
        return "WFLYSEC0048: Vault Configuration commands in WildFly for CLI:";
    }
    @Override
    public final String vaultConfigurationTitle() {
        return String.format(getLoggingLocale(), vaultConfigurationTitle$str());
    }
    protected String noConsole$str() {
        return "WFLYSEC0049: No console.";
    }
    @Override
    public final String noConsole() {
        return String.format(getLoggingLocale(), noConsole$str());
    }
    protected String initializingVault$str() {
        return "WFLYSEC0056: Initializing Vault";
    }
    @Override
    public final String initializingVault() {
        return String.format(getLoggingLocale(), initializingVault$str());
    }
    protected String vaultInitialized$str() {
        return "WFLYSEC0057: Vault is initialized and ready for use";
    }
    @Override
    public final String vaultInitialized() {
        return String.format(getLoggingLocale(), vaultInitialized$str());
    }
    protected String handshakeComplete$str() {
        return "WFLYSEC0058: Handshake with Vault complete";
    }
    @Override
    public final String handshakeComplete() {
        return String.format(getLoggingLocale(), handshakeComplete$str());
    }
    protected String exceptionEncountered$str() {
        return "WFLYSEC0059: Exception encountered:";
    }
    @Override
    public final String exceptionEncountered() {
        return String.format(getLoggingLocale(), exceptionEncountered$str());
    }
    protected String problemParsingCommandLineParameters$str() {
        return "WFLYSEC0068: Problem while parsing command line parameters:";
    }
    @Override
    public final String problemParsingCommandLineParameters() {
        return String.format(getLoggingLocale(), problemParsingCommandLineParameters$str());
    }
    protected String cmdLineSecuredAttributeAlreadyExists$str() {
        return "WFLYSEC0080: Secured attribute (password) already exists.";
    }
    @Override
    public final String cmdLineSecuredAttributeAlreadyExists() {
        return String.format(getLoggingLocale(), cmdLineSecuredAttributeAlreadyExists$str());
    }
    protected String cmdLineSecuredAttributeDoesNotExist$str() {
        return "WFLYSEC0081: Secured attribute (password) doesn't exist.";
    }
    @Override
    public final String cmdLineSecuredAttributeDoesNotExist() {
        return String.format(getLoggingLocale(), cmdLineSecuredAttributeDoesNotExist$str());
    }
    protected String enterEncryptionDirectory$str() {
        return "Enter directory to store encrypted files:";
    }
    @Override
    public final String enterEncryptionDirectory() {
        return String.format(getLoggingLocale(), enterEncryptionDirectory$str());
    }
    protected String enterKeyStoreURL$str() {
        return "Enter Keystore URL:";
    }
    @Override
    public final String enterKeyStoreURL() {
        return String.format(getLoggingLocale(), enterKeyStoreURL$str());
    }
    protected String enterKeyStorePassword$str() {
        return "Enter Keystore password:";
    }
    @Override
    public final String enterKeyStorePassword() {
        return String.format(getLoggingLocale(), enterKeyStorePassword$str());
    }
    protected String enterKeyStorePasswordAgain$str() {
        return "Enter Keystore password again:";
    }
    @Override
    public final String enterKeyStorePasswordAgain() {
        return String.format(getLoggingLocale(), enterKeyStorePasswordAgain$str());
    }
    protected String enterSalt$str() {
        return "Enter 8 character salt:";
    }
    @Override
    public final String enterSalt() {
        return String.format(getLoggingLocale(), enterSalt$str());
    }
    protected String enterIterationCount$str() {
        return "Enter iteration count as a number (e.g.: 44):";
    }
    @Override
    public final String enterIterationCount() {
        return String.format(getLoggingLocale(), enterIterationCount$str());
    }
    protected String enterKeyStoreAlias$str() {
        return "Enter Keystore Alias:";
    }
    @Override
    public final String enterKeyStoreAlias() {
        return String.format(getLoggingLocale(), enterKeyStoreAlias$str());
    }
    protected String enterYourPassword$str() {
        return "Enter your password:";
    }
    @Override
    public final String enterYourPassword() {
        return String.format(getLoggingLocale(), enterYourPassword$str());
    }
    protected String enterYourPasswordAgain$str() {
        return "Enter your password again:";
    }
    @Override
    public final String enterYourPasswordAgain() {
        return String.format(getLoggingLocale(), enterYourPasswordAgain$str());
    }
    protected String passwordsDoNotMatch$str() {
        return "Values entered don't match";
    }
    @Override
    public final String passwordsDoNotMatch() {
        return String.format(getLoggingLocale(), passwordsDoNotMatch$str());
    }
    protected String passwordsMatch$str() {
        return "Values match";
    }
    @Override
    public final String passwordsMatch() {
        return String.format(getLoggingLocale(), passwordsMatch$str());
    }
    protected String interactionCommandOptions$str() {
        return "Please enter a Digit::  0: Store a secured attribute  1: Check whether a secured attribute exists  2: Remove secured attribute  3: Exit";
    }
    @Override
    public final String interactionCommandOptions() {
        return String.format(getLoggingLocale(), interactionCommandOptions$str());
    }
    protected String taskStoreSecuredAttribute$str() {
        return "Task: Store a secured attribute";
    }
    @Override
    public final String taskStoreSecuredAttribute() {
        return String.format(getLoggingLocale(), taskStoreSecuredAttribute$str());
    }
    protected String interactivePromptSecureAttributeValue$str() {
        return "Please enter secured attribute value (such as password)";
    }
    @Override
    public final String interactivePromptSecureAttributeValue() {
        return String.format(getLoggingLocale(), interactivePromptSecureAttributeValue$str());
    }
    protected String interactivePromptVaultBlock$str() {
        return "Enter Vault Block:";
    }
    @Override
    public final String interactivePromptVaultBlock() {
        return String.format(getLoggingLocale(), interactivePromptVaultBlock$str());
    }
    protected String interactivePromptAttributeName$str() {
        return "Enter Attribute Name:";
    }
    @Override
    public final String interactivePromptAttributeName() {
        return String.format(getLoggingLocale(), interactivePromptAttributeName$str());
    }
    protected String problemOcurred$str() {
        return "Problem occurred:";
    }
    @Override
    public final String problemOcurred() {
        return String.format(getLoggingLocale(), problemOcurred$str());
    }
    protected String interactiveCommandString$str() {
        return "Please enter a Digit::   0: Start Interactive Session   1: Remove Interactive Session  2: Exit";
    }
    @Override
    public final String interactiveCommandString() {
        return String.format(getLoggingLocale(), interactiveCommandString$str());
    }
    protected String startingInteractiveSession$str() {
        return "Starting an interactive session";
    }
    @Override
    public final String startingInteractiveSession() {
        return String.format(getLoggingLocale(), startingInteractiveSession$str());
    }
    protected String removingInteractiveSession$str() {
        return "Removing the current interactive session";
    }
    @Override
    public final String removingInteractiveSession() {
        return String.format(getLoggingLocale(), removingInteractiveSession$str());
    }
    protected String cmdLineKeyStoreURL$str() {
        return "Keystore URL";
    }
    @Override
    public final String cmdLineKeyStoreURL() {
        return String.format(getLoggingLocale(), cmdLineKeyStoreURL$str());
    }
    protected String cmdLineKeyStorePassword$str() {
        return "Keystore password";
    }
    @Override
    public final String cmdLineKeyStorePassword() {
        return String.format(getLoggingLocale(), cmdLineKeyStorePassword$str());
    }
    protected String cmdLineEncryptionDirectory$str() {
        return "Directory containing encrypted files";
    }
    @Override
    public final String cmdLineEncryptionDirectory() {
        return String.format(getLoggingLocale(), cmdLineEncryptionDirectory$str());
    }
    protected String cmdLineSalt$str() {
        return "8 character salt";
    }
    @Override
    public final String cmdLineSalt() {
        return String.format(getLoggingLocale(), cmdLineSalt$str());
    }
    protected String cmdLineIterationCount$str() {
        return "Iteration count";
    }
    @Override
    public final String cmdLineIterationCount() {
        return String.format(getLoggingLocale(), cmdLineIterationCount$str());
    }
    protected String cmdLineVaultKeyStoreAlias$str() {
        return "Vault keystore alias";
    }
    @Override
    public final String cmdLineVaultKeyStoreAlias() {
        return String.format(getLoggingLocale(), cmdLineVaultKeyStoreAlias$str());
    }
    protected String cmdLineVaultBlock$str() {
        return "Vault block";
    }
    @Override
    public final String cmdLineVaultBlock() {
        return String.format(getLoggingLocale(), cmdLineVaultBlock$str());
    }
    protected String cmdLineAttributeName$str() {
        return "Attribute name";
    }
    @Override
    public final String cmdLineAttributeName() {
        return String.format(getLoggingLocale(), cmdLineAttributeName$str());
    }
    protected String cmdLineAutomaticallyCreateKeystore$str() {
        return "Automatically create keystore when it doesn't exist";
    }
    @Override
    public final String cmdLineAutomaticallyCreateKeystore() {
        return String.format(getLoggingLocale(), cmdLineAutomaticallyCreateKeystore$str());
    }
    protected String cmdLineSecuredAttribute$str() {
        return "Secured attribute value (such as password) to store";
    }
    @Override
    public final String cmdLineSecuredAttribute() {
        return String.format(getLoggingLocale(), cmdLineSecuredAttribute$str());
    }
    protected String cmdLineCheckAttribute$str() {
        return "Check whether the secured attribute already exists in the Vault";
    }
    @Override
    public final String cmdLineCheckAttribute() {
        return String.format(getLoggingLocale(), cmdLineCheckAttribute$str());
    }
    protected String cmdLineRemoveSecuredAttribute$str() {
        return "Remove secured attribute from the Vault";
    }
    @Override
    public final String cmdLineRemoveSecuredAttribute() {
        return String.format(getLoggingLocale(), cmdLineRemoveSecuredAttribute$str());
    }
    protected String cmdLineHelp$str() {
        return "Help";
    }
    @Override
    public final String cmdLineHelp() {
        return String.format(getLoggingLocale(), cmdLineHelp$str());
    }
    protected String messageAttributeRemovedSuccessfuly$str() {
        return "Secured attribute %s has been successfuly removed from vault";
    }
    @Override
    public final String messageAttributeRemovedSuccessfuly(final String displayFormattedAttribute) {
        return String.format(getLoggingLocale(), messageAttributeRemovedSuccessfuly$str(), displayFormattedAttribute);
    }
    protected String messageAttributeNotRemoved$str() {
        return "Secured attribute %s was not removed from vault, check whether it exist";
    }
    @Override
    public final String messageAttributeNotRemoved(final String displayFormattedAttribute) {
        return String.format(getLoggingLocale(), messageAttributeNotRemoved$str(), displayFormattedAttribute);
    }
    protected String actionNotSpecified$str() {
        return "Action not specified";
    }
    @Override
    public final String actionNotSpecified() {
        return String.format(getLoggingLocale(), actionNotSpecified$str());
    }
    protected String interactivePromptSecureAttributeValueAgain$str() {
        return "Please enter secured attribute value again";
    }
    @Override
    public final String interactivePromptSecureAttributeValueAgain() {
        return String.format(getLoggingLocale(), interactivePromptSecureAttributeValueAgain$str());
    }
    protected String taskVerifySecuredAttributeExists$str() {
        return "Task: Verify whether a secured attribute exists";
    }
    @Override
    public final String taskVerifySecuredAttributeExists() {
        return String.format(getLoggingLocale(), taskVerifySecuredAttributeExists$str());
    }
    protected String interactiveMessageNoValueStored$str() {
        return "No value has been stored for %s";
    }
    @Override
    public final String interactiveMessageNoValueStored(final String displayFormattedAttribute) {
        return String.format(getLoggingLocale(), interactiveMessageNoValueStored$str(), displayFormattedAttribute);
    }
    protected String interactiveMessageValueStored$str() {
        return "A value exists for %s";
    }
    @Override
    public final String interactiveMessageValueStored(final String displayFormattedAttribute) {
        return String.format(getLoggingLocale(), interactiveMessageValueStored$str(), displayFormattedAttribute);
    }
    protected String taskRemoveSecuredAttribute$str() {
        return "Task: Remove secured attribute";
    }
    @Override
    public final String taskRemoveSecuredAttribute() {
        return String.format(getLoggingLocale(), taskRemoveSecuredAttribute$str());
    }
}
