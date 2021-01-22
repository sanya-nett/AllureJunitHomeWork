pipeline {
    agent any

    parameters {
        gitParameter(
            branch: '',
            branchFilter: 'origin.*/(.*)',
            defaultValue: 'master',
            description: 'Select the target branch for run special tests',
            name: 'Branch',
            listSize: '1',
            quickFilterEnabled: true,
            selectedValue: 'DEFAULT',
            sortMode: 'ASCENDING_SMART',
            tagFilter: '*',
            type: 'PT_BRANCH',
        )
        choice(
            name: 'Browser',
            choices: ['chrome', 'firefox'],
            description: 'Select the target browser in Selenoid',
        )
    }

    triggers {
        githubPush()
        cron('0 1 * * *')
    }

    stages {
        stage('UI Tests') {
            agent {
                docker { image 'maven:3.6.3-openjdk-11' }
            }
            steps {
                sh "env"
                git url: 'https://github.com/sanya-nett/AllureJunitHomeWork', branch: params.Branch
                sh 'mvn clean test -Dmaven.test.failure.ignore=true'
                stash name: 'source', includes: '**/*.*'
            }
        }
        stage('Backup and reports') {
            steps {
                unstash 'source'
                archiveArtifacts artifacts: '**/target/', fingerprint: true
            }
            post {
                always {
                    script {
                        allure results: [[ path: 'target/allure-results' ]]
                        def testSummary = junit testResults: '**/target/surefire-reports/*.xml'
                        sendNotification(testSummary)
                    }
                }
            }
        }
    }
}

// Optional: move to shared libs
void sendNotification(junitInfo) {
    def messageSubject = "Test build '${currentBuild.rawBuild.project.displayName}'"
    def messageBody = "Build number: ${env.BUILD_NUMBER} \n"
    messageBody += "Branch: ${params.Branch} \n"
    messageBody += "Finished with status: ${currentBuild.currentResult} \n"
    messageBody += "Test Summary - ${junitInfo.totalCount}\nPassed: ${junitInfo.passCount}, "
    messageBody += "Failures: ${junitInfo.failCount}, Skipped: ${junitInfo.skipCount}\n"
    messageBody += "Duration: ${currentBuild.durationString.replace('and counting', '')}\n"
    messageBody += "More info at: ${env.BUILD_URL}"

    // Slack notification
    def buildColor = (currentBuild.currentResult == 'SUCCESS') ? 'good' : 'danger'
    slackSend color: buildColor,
        message: "*" + messageSubject + "*\n" + messageBody

    // Email notification
    emailext subject: messageSubject,
        body: messageBody,
        to: "scherba.alexander@gmail.com"
        recipientProviders: [
            developers(),
            requestor(),
        ]
}
