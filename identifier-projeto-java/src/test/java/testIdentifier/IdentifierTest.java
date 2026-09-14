package testIdentifier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Suíte de testes do programa Identifier, derivada por
 * Particionamento em Classes de Equivalência (ECP) e complementada
 * por Análise de Valor Limite (AVL).
 *
 * Especificação: um identificador é válido se, e somente se:
 *   - começa com uma letra (A-Z, a-z);
 *   - contém apenas letras ou dígitos nas posições seguintes;
 *   - possui comprimento entre 1 e 6 caracteres (inclusive).
 *
 * Classes de equivalência consideradas:
 *   CE1 - Comprimento válido (1 a 6)          [válida]
 *   CE2 - Comprimento inválido (0 ou > 6)     [inválida]
 *   CE3 - Primeiro caractere é letra          [válida]
 *   CE4 - Primeiro caractere não é letra      [inválida]
 *   CE5 - Demais caracteres são letra/dígito  [válida]
 *   CE6 - Demais caracteres contêm símbolo    [inválida]
 *
 * Cada teste segue a estrutura: Setup -> Invocation -> Assessment (Assert).
 */
class IdentifierTest {

    private Identifier identifier;

    @BeforeEach
    void setUp() {
        // Setup: inicializa uma nova instância do objeto sob teste antes de cada caso.
        identifier = new Identifier();
    }

    // ---------------------------------------------------------------
    // Casos derivados do Particionamento em Classes de Equivalência
    // ---------------------------------------------------------------

    @Test
    @DisplayName("PE-01: identificador só com letras, comprimento típico -> Válido (CE1+CE3+CE5)")
    void testSomenteLetrasComprimentoTipico() {
        // Setup
        String entrada = "abc";

        // Invocation
        boolean resultado = identifier.validateIdentifier(entrada);

        // Assessment
        assertTrue(resultado, "\"abc\" deveria ser válido: começa com letra e só tem letras");
    }

    @Test
    @DisplayName("PE-02: letras e dígitos misturados, comprimento máximo (6) -> Válido (CE1+CE3+CE5)")
    void testLetrasEDigitosComprimentoMaximo() {
        // Setup
        String entrada = "a1b2c3";

        // Invocation
        boolean resultado = identifier.validateIdentifier(entrada);

        // Assessment
        assertTrue(resultado, "\"a1b2c3\" deveria ser válido: 6 caracteres, letra inicial, letras/dígitos");
    }

    @Test
    @DisplayName("PE-03: comprimento mínimo (1 caractere) -> Válido (CE1+CE3)")
    void testComprimentoMinimo() {
        // Setup
        String entrada = "a";

        // Invocation
        boolean resultado = identifier.validateIdentifier(entrada);

        // Assessment
        assertTrue(resultado, "\"a\" deveria ser válido: 1 letra, dentro do limite mínimo");
    }

    @Test
    @DisplayName("PE-04: string vazia -> Inválido (CE2, comprimento = 0)")
    void testStringVazia() {
        // Setup
        String entrada = "";

        // Invocation
        boolean resultado = identifier.validateIdentifier(entrada);

        // Assessment
        assertFalse(resultado, "String vazia deveria ser inválida (comprimento = 0)");
    }

    @Test
    @DisplayName("PE-05: comprimento acima do máximo (7) -> Inválido (CE2)")
    void testComprimentoAcimaDoMaximo() {
        // Setup
        String entrada = "abcdefg";

        // Invocation
        boolean resultado = identifier.validateIdentifier(entrada);

        // Assessment
        assertFalse(resultado, "\"abcdefg\" tem 7 caracteres, deveria ser inválido");
    }

    @Test
    @DisplayName("PE-06: primeiro caractere é dígito -> Inválido (CE4)")
    void testPrimeiroCaractereDigito() {
        // Setup
        String entrada = "1abcde";

        // Invocation
        boolean resultado = identifier.validateIdentifier(entrada);

        // Assessment
        assertFalse(resultado, "\"1abcde\" começa com dígito, deveria ser inválido");
    }

    @Test
    @DisplayName("PE-07: primeiro caractere é símbolo -> Inválido (CE4)")
    void testPrimeiroCaractereSimbolo() {
        // Setup
        String entrada = "_abc";

        // Invocation
        boolean resultado = identifier.validateIdentifier(entrada);

        // Assessment
        assertFalse(resultado, "\"_abc\" começa com símbolo, deveria ser inválido");
    }

    @Test
    @DisplayName("PE-08: caractere subsequente é símbolo -> Inválido (CE6)")
    void testCaractereSubsequenteSimbolo() {
        // Setup
        String entrada = "ab#cd";

        // Invocation
        boolean resultado = identifier.validateIdentifier(entrada);

        // Assessment
        assertFalse(resultado, "\"ab#cd\" contém '#', deveria ser inválido");
    }

    @Test
    @DisplayName("PE-09: caractere subsequente é espaço -> Inválido (CE6)")
    void testCaractereSubsequenteEspaco() {
        // Setup
        String entrada = "ab cd";

        // Invocation
        boolean resultado = identifier.validateIdentifier(entrada);

        // Assessment
        assertFalse(resultado, "\"ab cd\" contém espaço, deveria ser inválido");
    }

    // ---------------------------------------------------------------
    // Casos complementares de Análise de Valor Limite (comprimento)
    // ---------------------------------------------------------------

    @Test
    @DisplayName("VL-00: comprimento = 0 (limite inferior - 1) -> Inválido")
    void testValorLimiteComprimentoZero() {
        // Setup
        String entrada = "";

        // Invocation
        boolean resultado = identifier.validateIdentifier(entrada);

        // Assessment
        assertFalse(resultado);
    }

    @Test
    @DisplayName("VL-01: comprimento = 1 (limite inferior) -> Válido")
    void testValorLimiteComprimentoUm() {
        // Setup
        String entrada = "a";

        // Invocation
        boolean resultado = identifier.validateIdentifier(entrada);

        // Assessment
        assertTrue(resultado);
    }

    @Test
    @DisplayName("VL-06: comprimento = 6 (limite superior) -> Válido")
    void testValorLimiteComprimentoSeis() {
        // Setup
        String entrada = "abcdef";

        // Invocation
        boolean resultado = identifier.validateIdentifier(entrada);

        // Assessment
        assertTrue(resultado);
    }

    @Test
    @DisplayName("VL-07: comprimento = 7 (limite superior + 1) -> Inválido")
    void testValorLimiteComprimentoSete() {
        // Setup
        String entrada = "abcdefg";

        // Invocation
        boolean resultado = identifier.validateIdentifier(entrada);

        // Assessment
        assertFalse(resultado);
    }
}
