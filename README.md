# financial-market

## Descrição

Este projeto implementa um programa de linha de comando (CLI) para o INMA-UFMS (Instituto de Matemática da Universidade Federal de Mato Grosso do Sul). 

## Funcionalidade

O programa processa uma lista de operações do mercado financeiro de ações e calcula o imposto em cada operação. Cada operação é detalhada em JSON e inclui detalhes como tipo de operação (compra ou venda), custo unitário da ação e quantidade negociada.

## Formato de Entrada

- As entradas são recebidas em formato JSON através da entrada padrão (stdin).
- Cada operação contém os campos:
    - `operation`: Indica se é uma operação de compra (`buy`) ou venda (`sell`).
    - `unit-cost`: Preço unitário da ação.
    - `quantity`: Quantidade de ações negociadas.
- As operações são processadas na ordem em que são recebidas.
- Podemos ter mais de uma lista de operações (array de operações) em uma única entrada.
- Cada array deve ser processado separadamente.
- As operações estarão na ordem em que elas ocorreram, ou seja, a segunda operação na lista aconteceu depois da primeira e assim por diante.
- Cada linha é uma simulação independente, logo não deve manter o estado obtido em uma linha para as outras.
- Exemplo de entrada:
```js
  [{"operation": "buy", "unit-cost": 10.00, "quantity": 10000},
  {"operation": "sell", "unit-cost": 20.00, "quantity": 5000}]
  [{"operation":"buy", "unit-cost":20.00, "quantity": 10000},
  {"operation":"sell", "unit-cost":10.00, "quantity": 5000}]
```


## Formato de Saída

- A saída é uma lista em formato JSON emitida pela saída padrão (stdout).
- Cada elemento da lista contém o campo `tax`, que representa o imposto pago na operação correspondente.
- A lista retornada pelo programa deve ter o mesmo tamanho da lista de operações processadas na entrada.
- Por exemplo, se foram processadas três operações (buy, buy, sell), o retorno do programa deve ser uma lista
  com três valores que representam o imposto pago em cada operação.
- Exemplo de saída:
```js
  [{"tax": 0.00}, {"tax": 1000.00}]
  [{"tax": 0.00}, {"tax": 1000.00}]
```


## Regras de Negocio

1. **Imposto sobre Lucros**: Imposto de 20% é aplicado sobre o lucro de operações de venda.
2. **Cálculo do Lucro**: Baseado no preço médio ponderado das ações compradas.
3. **Recálculo do Preço Médio**: Ocorre a cada nova compra.
4. **Dedução de Prejuízos**: Prejuízos em vendas podem ser usados para deduzir lucros futuros.
5. **Limite para Isenção de Imposto**: Operações até R$ 20.000,00 são isentas de imposto.
6. **Imposto em Compras**: Não há imposto em operações de compra.
7. **Restrição de Venda**: Não é possível vender ações não possuídas.

## Exemplos de Casos de uso

- Exemplos estao em assets/use_cases

## Desenho do fluxograma da aplicação: [link-do-excalidraw](https://excalidraw.com/#room=591bff02bea137054930,-2kZhkcQRabe_hCf1O8lcw)# financial-market-new
# financial-market-new
