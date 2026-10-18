# RootBoost Game Space

> Um Game Space simples, visual e em português, inspirado no painel AstralOpt/AZenith enviado pelo usuário.

Este repositório inicia uma nova linha do projeto usando o pacote AstralOpt fornecido como referência e base de empacotamento. A proposta é reconstruir a experiência com uma interface própria em português, sem carregar a arquitetura pesada do antigo RootBoost.

![Referência visual do Game Space](docs/game-space-reference.jpg)

## O que esta entrega contém

- `dist/AstralOpt-2.0-RootBoost-GameSpace-base.zip`: pacote base fornecido pelo usuário, preservado para teste no Magisk.
- `app/`: APK launcher próprio do RootBoost Game Space, com Activity visível na gaveta.
- `module/`: módulo Magisk que instala o APK e não inicia daemon de desempenho.
- documentação da arquitetura nova;
- changelog em português;
- regras para artes, sessões e rollback;
- roadmap para o painel RootBoost Game Space.

## Experiência planejada

### Jogos

- carrossel horizontal de jogos instalados;
- ícone e arte dinâmica por jogo;
- wallpaper com blur e escurecimento;
- nome, versão e pacote;
- botão grande **Jogar**;
- status de root, temperatura, bateria, perfil e módulo.

### Configurações

- Geral;
- Desempenho;
- Aparência;
- Áudio;
- configurações individuais por jogo.

### Perfis

- Desempenho;
- Equilibrado;
- Eco.

O modo automático e o monitoramento residente ficam desligados por padrão.

## Instalação da alpha atual

1. Baixe o ZIP `RootBoost-GameSpace-0.2.0-alpha` na release.
2. Instale-o pelo Magisk como módulo.
3. Reinicie se o Magisk solicitar.
4. Procure **RootBoost Game Space** na gaveta de aplicativos.
5. Abra o painel e selecione um jogo.

O APK controlador é instalado pelo `customize.sh`. O módulo não possui `service.sh`, portanto não inicia um monitor permanente apenas por ser instalado.

## Artes dos jogos

A implementação usará, nesta ordem:

1. ícone do jogo instalado;
2. imagem escolhida pelo usuário;
3. asset com licença compatível;
4. fundo gerado automaticamente a partir do ícone.

Não são embutidas capas comerciais de jogos neste repositório sem autorização.

## Base AstralOpt

O ZIP base continua identificado como AstralOpt porque contém o APK e o binário originais compilados. Ele não é apresentado como código-fonte RootBoost. A implementação própria do painel em português será adicionada em etapas, com o binário de sessão e o APK controlador separados.

## Aviso de teste

Use primeiro em aparelho secundário ou faça backup do módulo no Magisk. O pacote base pode alterar threads de jogos de acordo com o suporte do kernel. O RootBoost Game Space próprio só será considerado estável depois de validar instalação, encerramento de sessão e rollback em aparelho real.
