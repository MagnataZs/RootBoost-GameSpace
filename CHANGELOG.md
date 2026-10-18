# Changelog — RootBoost Game Space

## 0.1.0-alpha — A fundação visual

### Destaques

- Novo projeto separado do RootBoost legado.
- AstralOpt preservado como base fornecida pelo usuário.
- Documentação inicial em português.
- Arquitetura definida para um Game Space visual e simples.
- Fluxo de sessão com snapshot e rollback especificado.
- Política de artes de jogos documentada.
- Roadmap dividido em etapas pequenas para evitar daemons e controles excessivos.

### O que ainda não está nesta alpha

- painel próprio em português dentro do APK;
- tradução do APK original AstralOpt;
- perfis RootBoost próprios;
- rollback implementado no binário base;
- escudo de chamadas integrado ao Game Space;
- wallpapers automáticos por jogo.

## 0.2.0-alpha — APK controlador e módulo limpo

### Incluído

- APK Android real com Activity launcher.
- Ícone **RootBoost Game Space** visível na gaveta.
- Carrossel de jogos reconhecidos pela categoria de jogos do Android.
- Arte de fundo gerada a partir do app selecionado.
- Status de bateria, temperatura e root.
- Botão **Jogar** para abrir o jogo selecionado.
- Aba de configurações em português.
- Módulo Magisk que instala o APK automaticamente.
- Módulo sem `service.sh` e sem daemon de desempenho persistente.

### Correção importante

Esta versão corrige o problema da alpha anterior: o APK do painel agora é um aplicativo launcher de verdade. A versão AstralOpt anterior não aparecia na gaveta porque seu APK era usado como processo `app_process`, não como Activity.

## Próxima versão — 0.3.0-alpha

- APK controlador próprio em português;
- tela Jogos com carrossel;
- leitura de apps categorizados como jogos;
- botão Jogar;
- tela de configurações por jogo;
- status de root e temperatura sob demanda.

## Roadmap 0.3.0

- sessão temporária;
- snapshot e restauração;
- perfis Desempenho, Equilibrado e Eco;
- escudo de comunicação;
- logs e diagnóstico.

## Roadmap 1.0

- Game Space completo;
- artes personalizadas;
- suporte retrato/paisagem;
- compatibilidade por SoC;
- release estável para uso diário.
