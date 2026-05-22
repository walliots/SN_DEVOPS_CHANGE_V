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
                snDevOpsChange()
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