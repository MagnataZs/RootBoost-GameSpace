# RootBoost Game Space — arquitetura planejada

## Conceito

Um Game Space em português, inspirado no fluxo visual do painel enviado pelo usuário: carrossel de jogos, arte dinâmica, botão **Jogar**, status do aparelho e configurações por jogo.

## Fluxo de sessão

```text
Selecionar jogo
    ↓
Ler root e compatibilidade
    ↓
Salvar snapshot do sistema
    ↓
Aplicar perfil temporário
    ↓
Abrir jogo
    ↓
Monitorar apenas durante a sessão
    ↓
Detectar fechamento
    ↓
Restaurar snapshot
```

## Princípios

- Nenhum daemon de desempenho ativo quando o usuário desliga o modo automático.
- Nenhuma arte comercial embutida sem licença: usar ícone instalado, wallpaper fornecido pelo usuário ou asset licenciado.
- Nenhum `drop_caches`, fake temperature, bypass de proteção térmica ou App Killer global.
- Toda alteração temporária precisa de snapshot e rollback.
- Recursos incompatíveis devem aparecer como indisponíveis, não como botões que falham silenciosamente.
