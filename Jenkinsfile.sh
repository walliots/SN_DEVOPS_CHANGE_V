pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('ServiceNow Change') {
            steps {
                snDevOpsChange(
                    toolId: '3623b2f8938d075000263f018bba10e7'
                )
            }
        }
    }

    post {
        success {
            echo 'Build realizado com sucesso!'
        }
        failure {
            echo 'Build falhou!'
        }
    }
}