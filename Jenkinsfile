pipeline {
    agent any

    environment {
        SONARQUBE_URL = "http://localhost:9000/"
        SONARQUBE_TOKEN = "squ_263decad84ae28a5542f1167769d1ea84ecd86af"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Code Analysis with SonarQube') {
            steps {
                script {
                    bat '''
                        mvn clean verify sonar:sonar \
                          -Dsonar.projectKey=accounts \
                          -Dsonar.host.url=${SONARQUBE_URL} \
                          -Dsonar.login=${SONARQUBE_TOKEN} \
                          -Dsonar.java.binaries=target/classes
                    '''
                }
            }
        }

        /*
        stage('SonarQube Analysis') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'sonar-token-jen', variable: 'SONAR_TOKEN_JEN')]) {
                        withSonarQubeEnv('SonarQube') {
                            bat """
                                mvnw.cmd clean org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar \
                                -Dsonar.host.url=http://localhost:9000 \
                                -Dsonar.token=%SONAR_TOKEN_JEN% \
                                -Dsonar.java.binaries=target/classes \
                                -X
                            """
                        }
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    bat '''wsl ansible-playbook \
                        -i /mnt/c/Users/anith/Documents/2nd_sem/Jenkins/AccountApplication/hosts.ini \
                        /mnt/c/Users/anith/Documents/2nd_sem/Jenkins/AccountApplication/deploy-docker.yml'''
                }
            }
        }
        */
    }

    post {
        always {
            echo "Cleaning up"
        }
        success {
            echo "Build succeeded"
        }
        failure {
            echo "Build failed"
        }
    }
}
