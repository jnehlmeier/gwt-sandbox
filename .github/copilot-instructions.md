# GWT (Google Web Toolkit) Development

GWT is a Java-based web development framework that compiles Java code to JavaScript. This repository contains the core GWT SDK, tools, and sample applications.

**ALWAYS reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.**

## Working Effectively

### Prerequisites and Setup
- Ensure Java 11+ is installed and available (`java -version`)
- Ensure Apache Ant is installed and available (`ant -version`)
- **CRITICAL**: Clone the GWT tools repository as a sibling directory:
  ```bash
  cd /path/to/parent/directory
  git clone https://github.com/gwtproject/tools.git
  git clone https://github.com/jnehlmeier/gwt-sandbox.git
  ```
- **ALWAYS** set these environment variables before any build operations:
  ```bash
  export TZ=America/Los_Angeles
  export ANT_OPTS='-Dfile.encoding=UTF-8 -Xmx2g'
  ```

### Core Build Commands
- **NEVER CANCEL builds or long-running commands** - they can take significant time but will complete successfully
- Basic development build (compiles without documentation):
  ```bash
  ant clean buildonly
  ```
  **Takes ~3.5 minutes. NEVER CANCEL. Set timeout to 10+ minutes.**

- Full development distribution (no docs, but creates ZIP):
  ```bash
  ant clean dist-dev
  ```
  **Takes ~3.5-4 minutes. NEVER CANCEL. Set timeout to 10+ minutes.**

- Complete distribution with documentation:
  ```bash
  ant clean dist
  ```
  **Takes ~7-8 minutes. NEVER CANCEL. Set timeout to 15+ minutes.**

### Code Quality and Validation
- **CRITICAL**: Always run verification commands before committing:
  ```bash
  ant compile.tests apicheck checkstyle
  ```
  **Takes ~9 minutes. NEVER CANCEL. Set timeout to 20+ minutes.**

- Quick JRE-only tests (fast validation):
  ```bash
  cd user && ant test.nongwt
  ```
  **Takes ~2 minutes. NEVER CANCEL. Set timeout to 5+ minutes.**

## Manual Validation Requirements

### Build Validation Scenarios
After any changes, **ALWAYS** run through these validation steps:

1. **Full Build Test**:
   ```bash
   ant clean dist-dev
   ```
   Verify it completes successfully and produces `build/dist/gwt-*.zip`

2. **Code Quality Check**:
   ```bash
   ant compile.tests apicheck checkstyle
   ```
   Verify no new checkstyle violations or API compatibility issues

3. **Quick Test Suite**:
   ```bash
   cd user && ant test.nongwt
   ```
   Verify all JRE tests pass

4. **Sample Compilation Test**:
   ```bash
   ant samples
   ```
   **Takes ~3 minutes. NEVER CANCEL. Set timeout to 10+ minutes.**
   Verify samples compile successfully

### Testing Specific Modules
- Test dev module only:
  ```bash
  ant dev -Dtarget=test -Dgwt.junit.testcase.dev.core.includes="**/YourTest.class"
  ```

- Test specific JRE suites:
  ```bash
  cd user && ant test.nongwt -Dgwt.nongwt.testcase.includes="**/I18NJreSuite.class"
  ```

## Project Structure and Key Modules

### Core Modules
- **dev/**: GWT compiler and development tools
- **user/**: GWT user API and JRE emulation libraries  
- **dev/codeserver/**: SuperDevMode server for development
- **requestfactory/**: Request Factory library for client-server communication
- **servlet/**: Server-side servlet components
- **tools/**: Development and build tools
- **samples/**: Example applications demonstrating GWT features

### Sample Applications (For Testing Changes)
Built samples are available in `build/out/samples/*/war/`:
- **Hello**: Simple "Hello World" GWT application
- **Showcase**: Complex UI application demonstrating widgets
- **Mail**: Email client demonstrating RPC
- **DynaTable**: Dynamic table with server communication
- **JSON**: JSON parsing example
- **MobileWebApp**: Mobile-optimized web application (Maven-based)
- **Validation**: Bean validation example (Maven-based)

### Build Artifacts
After successful builds, find outputs in:
- **JARs**: `build/lib/gwt-*.jar`
- **Distribution ZIP**: `build/dist/gwt-*.zip`
- **Documentation**: `build/out/doc/javadoc/`
- **Compiled Samples**: `build/out/samples/*/war/`

## Advanced Development Workflows

### Module-Specific Testing
```bash
# Test only dev module
ant dev -Dtarget=test

# Test only user module  
ant user -Dtarget=test

# Test only codeserver
ant codeserver -Dtarget=test -Dtest.dev.disable=true

# Test only tools
ant tools -Dtarget=test -Dtest.dev.disable=true -Dtest.user.disable=true
```

### Filtering Tests
```bash
# Run specific test class
ant dev -Dtarget=test -Dgwt.junit.testcase.dev.core.includes="**/JsniRefTest.class"

# Run multiple test classes
ant dev -Dtarget=test -Dgwt.junit.testcase.dev.core.includes="**/JsniRefTest.class,**/JsParserTest.class"
```

### Maven-Based Samples
Some samples use Maven instead of Ant:
- **DynaTableRf**: `cd samples/dynatablerf && mvn clean package`
- **MobileWebApp**: `cd samples/mobilewebapp && mvn clean package` 
- **Validation**: `cd samples/validation && mvn clean package`

## Common Build Issues and Solutions

### Tools Directory Missing
**Error**: "Cannot find tools directory"
**Solution**: Ensure GWT tools repository is cloned as `../tools` relative to this repository

### Out of Memory Errors
**Error**: Java heap space or PermGen errors
**Solution**: Increase memory: `export ANT_OPTS='-Dfile.encoding=UTF-8 -Xmx4g'`

### Timezone-Related Test Failures
**Error**: Date/time test failures
**Solution**: Always set: `export TZ=America/Los_Angeles`

## Build Performance Notes

### Expected Timing (with tools repo available)
- **buildonly**: ~3.5 minutes
- **dist-dev**: ~3.5-4 minutes  
- **dist** (with docs): ~7-8 minutes
- **compile.tests apicheck checkstyle**: ~9 minutes
- **samples**: ~3 minutes
- **user test.nongwt**: ~2 minutes

### Critical Timeout Settings
- **Basic builds**: Set timeout to 10+ minutes minimum
- **Full builds with docs**: Set timeout to 15+ minutes minimum  
- **Test suites**: Set timeout to 20+ minutes minimum
- **Complete test runs**: Set timeout to 60+ minutes minimum

### **NEVER CANCEL** Long-Running Operations
- Builds may appear to hang but are processing - wait for completion
- Sample compilation includes GWT-to-JavaScript transpilation which is CPU-intensive
- Javadoc generation processes thousands of files
- Test suites may run thousands of individual tests

## CI/CD Integration Notes

The repository includes GitHub Actions workflows:
- **full-check.yml**: Complete build, test, and doc generation (runs nightly)
- **quick-check.yml**: Fast smoke tests for PRs (compile + checkstyle only)

Always ensure your changes pass the quick check commands before submitting PRs:
```bash
ant clean compile.tests dist doc checkstyle apicheck
```