pipeline {
    agent any
    environment {
        LOG_JUNIT_RESULTS = 'true'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Publish Test Results') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE'){
                 sh 'mvn test'
                }
                 junit 'target/surefire-reports/*.xml'
                            influxDbPublisher(selectedTarget: 'bowling_junit_data')
            }

        }
    }
}