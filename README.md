# PetFriends

Um projeto de exemplo com microsserviços relacionado ao contexto de 
pedido, transporte e amarzém de produtos para pets, incluindo:
- petfriends-almoxarifado
- petfriends-pedido
- petfriends-transporte

O projeto também utiliza um serviço de gateway e o load balancer Consul

## Como o projeto está estruturado

Cada serviço dentro do projeto foi criado um módulo para o Maven, 
com cada serviço tendo a sua pasta na raiz do projeto.

O helm foi utilizado para facilitar a configuração e a execução do k8s, 
se encontrando na pasta helm/petfriends.

## Como executar o projeto localmente
```bash
# ---------------------------- Kubernetes ----------------------------
# Na raiz do projeto, faça o seguinte:
minikube start --driver=docker --container-runtime=containerd --cpus=4 --memory=8192
chmod 777 docker-image-build.sh
./docker-image-build.sh # Builda todas as imagens Dockerfile
minikube image load petfriends/petfriends-almoxarifado
minikube image load petfriends/petfriends-pedido
minikube image load petfriends/petfriends-transporte
minikube image load petfriends/petfriends-gateway

helm install petfriends helm/petfriends

# ---------------------------- Docker Compose ----------------------------
# Na raiz do projeto, faça o seguinte:
docker-compose up --build
```

