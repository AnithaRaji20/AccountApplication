pipeline {
    agent any  // Ensure you have a correct agent to run the job
    
    environment {
		SONARQUBE_URL = "http://localhost:9000"
		SONARQUBE_TOKEN = "squ_263decad84ae28a5542f1167769d1ea84ecd86af"
	}
    

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from SCM (GitHub)
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Run Maven build command, skipping tests
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                // Run unit tests and generate code coverage report
                bat 'mvn test'
            }
        }
        
	stage('Code Analysis with SonarQube') {
    	steps {
       		 script {
          	  sh '''
                mvn clean verify sonar:sonar \
                  -Dsonar.projectKey=accounts \
                  -Dsonar.host.url=${SONARQUBE_URL} \
                  -Dsonar.login=${SONARQUBE_TOKEN} \
                  -Dsonar.java.binaries=target/classes
            	'''
       		 }
    		}
		}


        /*stage('SonarQube Analysis') {
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
                    // Use WSL to run the Ansible playbook in Ubuntu (inside WSL)
                    bat '''wsl ansible-playbook \
                        -i /mnt/c/Users/anith/Documents/2nd_sem/Jenkins/AccountApplication/hosts.ini \
                        /mnt/c/Users/anith/Documents/2nd_sem/Jenkins/AccountApplication/deploy-docker.yml'''
                }
            }
        }*/
    }

    post {
        always {
            // Clean up steps (e.g., remove temporary files, close connections, etc.)
            echo "Cleaning up"
        }

        success {
            // Actions on successful build (e.g., send success notifications)
            echo "Build succeeded"
        }

        failure {
            // Actions on failure (e.g., send failure notifications)
            echo "Build failed"
        }
    }
}
