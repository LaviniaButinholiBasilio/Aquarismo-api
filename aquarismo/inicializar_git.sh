#!/bin/bash
# ─────────────────────────────────────────────────────────────
# Script para inicializar o repositório Git com commits
# organizados conforme as boas práticas do projeto.
# Execute UMA VEZ após clonar / criar o repositório.
# ─────────────────────────────────────────────────────────────

set -e

echo "==> Inicializando repositório Git..."
git init
git checkout -b main

# ── 1. Estrutura inicial do projeto ───────────────────────────
git add pom.xml .gitignore
git commit -m "chore: inicialização do projeto Spring Boot com Maven"

# ── 2. Configurações e application.properties ─────────────────
git add src/main/resources/application.properties
git add src/main/java/com/aquarismo/AquarismoApplication.java
git add src/main/java/com/aquarismo/config/JacksonConfig.java
git commit -m "chore: configuração do datasource MySQL, JPA e Flyway"

# ── 3. Enums e Models ─────────────────────────────────────────
git add src/main/java/com/aquarismo/model/
git commit -m "feat: criação dos modelos de domínio (Tanque, Peixe, Reproducao, Venda, ParametroAgua)"

# ── 4. Migrations ─────────────────────────────────────────────
git add src/main/resources/db/migration/V1__create_tables.sql
git commit -m "feat: migration V1 - DDL completo das tabelas do sistema"

git add src/main/resources/db/migration/V2__seed_dados_iniciais.sql
git commit -m "feat: migration V2 - seed de dados iniciais para desenvolvimento"

# ── 5. Repositories ───────────────────────────────────────────
git add src/main/java/com/aquarismo/repository/
git commit -m "feat: criação dos repositories com queries customizadas (JPA)"

# ── 6. DTOs ───────────────────────────────────────────────────
git add src/main/java/com/aquarismo/dto/
git commit -m "feat: implementação dos DTOs de request (com validações) e response"

# ── 7. Exceptions ─────────────────────────────────────────────
git add src/main/java/com/aquarismo/exception/
git commit -m "feat: tratamento global de erros com @RestControllerAdvice"

# ── 8. Services ───────────────────────────────────────────────
git add src/main/java/com/aquarismo/service/TanqueService.java
git commit -m "feat: implementação do TanqueService com regras de negócio"

git add src/main/java/com/aquarismo/service/PeixeService.java
git commit -m "feat: implementação do PeixeService com busca e controle de linhagem"

git add src/main/java/com/aquarismo/service/ReproducaoService.java
git commit -m "feat: implementação do ReproducaoService com validação de sexo dos peixes"

git add src/main/java/com/aquarismo/service/VendaService.java
git commit -m "feat: implementação do VendaService com atualização automática de status do peixe"

git add src/main/java/com/aquarismo/service/ParametroAguaService.java
git commit -m "feat: implementação do ParametroAguaService com validação de parâmetros químicos"

# ── 9. Controllers ────────────────────────────────────────────
git add src/main/java/com/aquarismo/controller/
git commit -m "feat: criação dos endpoints REST versionados (/api/v1) para todos os módulos"

# ── 10. README ────────────────────────────────────────────────
git add README.md
git commit -m "docs: README com instruções de execução, endpoints e exemplos de uso"

# ── TAG v1.0 ──────────────────────────────────────────────────
git tag -a v1.0 -m "v1.0 - 1ª Entrega: Model e Banco de Dados"

echo ""
echo "✅ Repositório inicializado com sucesso!"
echo "   Commits criados: $(git log --oneline | wc -l)"
echo "   Tag criada: v1.0"
echo ""
echo "Para enviar ao GitHub:"
echo "   git remote add origin https://github.com/SEU_USUARIO/aquarismo-api.git"
echo "   git push -u origin main"
echo "   git push origin v1.0"
