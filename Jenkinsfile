pipeline {
    agent any

    environment {
        SONARQUBE = 'SonarQube'
        DOCKER_HUB_CREDENTIALS = 'dockerhub'
        DOCKER_HUB_NAMESPACE = 'tondockerhubusername'
        IMAGE_NAME = 'gestionevenementbackend'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/eskandergharbi/gestionevenementbackend.git'
            }
        }

        stage('Build & Tests') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Analyse SonarQube') {
            steps {
                withSonarQubeEnv("${SONARQUBE}") {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_HUB_NAMESPACE}/${IMAGE_NAME}:latest ."
            }
        }

        stage('Push Docker Image') {
            steps {
                docker.withRegistry('https://index.docker.io/v1/', DOCKER_HUB_CREDENTIALS) {
                    docker.image("${DOCKER_HUB_NAMESPACE}/${IMAGE_NAME}:latest").push()
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
