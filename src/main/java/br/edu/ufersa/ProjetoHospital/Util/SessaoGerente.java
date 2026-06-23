package br.edu.ufersa.ProjetoHospital.Util;

import br.edu.ufersa.ProjetoHospital.model.entities.Endereco;
import br.edu.ufersa.ProjetoHospital.model.entities.Gerente;

// Como o sistema ainda não tem cadastro de gerentes no banco, usamos um
// único gerente fixo (login "gerente" / senha "123") pra autorizar as
// ações de gerenciamento de médicos.
public class SessaoGerente {

    private static final Gerente GERENTE = new Gerente(
            "João Gerente",
            "12345678900",
            new Endereco(),
            "ADMIN001"
    );

    private SessaoGerente() {}

    public static Gerente getGerenteLogado() {
        return GERENTE;
    }
}