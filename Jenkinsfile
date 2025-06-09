pipeline {
    agent any

    environment {
        COMPOSE_PROJECT_NAME = 'things-organizer'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/baharehfakhriyeh/things-organizer.git'
            }
        }

        stage('Build & Test') {
            agent {
                    docker {
                                image 'maven:3.9.6-eclipse-temurin-17'
                                args '''
                                  --privileged
                                  -v /var/run/docker.sock:/var/run/docker.sock
                                  -v /c/Users/Asus/.m2:/root/.m2
                                '''.stripIndent()
                                reuseNode true
                            }
            }
            steps {
                // compile, run unit tests, package to local repo
                sh 'mvn clean install'
            }
        }

        stage('Package (skip tests)') {
            steps {
                // produce jars without re-running tests
                sh 'mvn package -DskipTests'
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    // list of directories and target image tags
                    def modules = [
                        [dir: 'config-server', image: 'bhrfkhr/config-server:1.0.0'],
                        [dir: 'naming-server', image: 'bhrfkhr/naming-server:1.0.0'],
                        [dir: 'api-gateway',  image: 'bhrfkhr/api-gateway:1.0.0'],
                        [dir: 'thing',        image: 'bhrfkhr/things-organizer-thing:1.0.0'],
                        [dir: 'content',      image: 'bhrfkhr/things-organizer-content:1.0.0'],
                        [dir: 'security',     image: 'bhrfkhr/things-organizer-security:1.0.0']
                    ]

                    modules.each { m ->
                        dir(m.dir) {
                            sh "docker build -t ${m.image} ."
                        }
                    }
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                // tear down any existing stack, then bring it up in detached mode
                sh 'docker-compose down || true'
                sh 'docker-compose up -d'
            }
        }
    }

    post {
        success {
            echo '✅ CI/CD pipeline finished: all modules built, tested, and running.'
        }
        failure {
            echo '❌ Pipeline failed. Check console output for errors.'
        }
    }
}
