package com.aurora.pix.Teste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.starkbank.Balance;
import com.starkbank.DynamicBrcode;
import com.starkbank.Project;
import com.starkbank.Settings;
import com.starkbank.Transaction;
import com.starkbank.Transfer;
import com.starkbank.utils.Generator;

public class Teste {
    public static void main(String[] args) throws Exception {

        String privateKeyEnv = System.getenv("AURORA_PIX_PRIVATE_KEY_STARK_BANK");
        if (privateKeyEnv == null || privateKeyEnv.isEmpty()) {
            System.err.println("A variável de ambiente *AURORA_PIX_PRIVATE_KEY_STARK_BANK* não está definida.");
            return;
        }
        privateKeyEnv = privateKeyEnv.replace("|", "\n");

        String PROJECT_ID = System.getenv("AURORA_PIX_PROJECT_FINCON_ID_STARK_BANK");
        if (PROJECT_ID == null || PROJECT_ID.isEmpty()) {
            System.err.println("A variável de ambiente *AURORA_PIX_PROJECT_FINCON_ID_STARK_BANK* não está definida.");
            return;
        }
        Project project = new Project(
                "sandbox",
                PROJECT_ID,
                privateKeyEnv);

        Settings.user = project;
        Settings.language = "pt-BR";
        
        int valorQrCodeDinamico = 10000; // 100,00
        valorQrCodeDinamico += (valorQrCodeDinamico * 0.01); // 1%
        createQrCodeDinamico(valorQrCodeDinamico);

        // createTransacaoEntreContas();

        // getTransacoes();

        // getListQrCodeDinamicos();

        // findTransacaoById("f5cc520b95824f229704d643c4f4a9cf");

        // getSaldo();

    }

    public static void createTransacaoPix() throws Exception {
        List<Transfer> transfers = new ArrayList<>();
        List<Transfer.Rule> rules = new ArrayList<>();
        rules.add(
                new Transfer.Rule(
                        "resendingLimit", // Set maximum number of retries if Payment fails due to systemic issues at
                                          // the receiver bank
                        5 // Our resending limit is 10 by default
                ));
        HashMap<String, Object> data2 = new HashMap<>();
        data2.put("amount", 100000000);
        data2.put("bankCode", "20018183"); // Pix
        data2.put("branchCode", "2201");
        data2.put("accountNumber", "76543-8");
        data2.put("accountType", "checking");
        data2.put("externalId", "my-internal-id-12345");
        data2.put("taxId", "594.739.480-42");
        data2.put("name", "Daenerys Targaryen Stormborn");
        data2.put("scheduled", "2020-11-11T15:01:39.903667+00:00");
        data2.put("tags", new String[] { "daenerys", "invoice/1234" });
        data2.put("rules", rules);
        transfers.add(new Transfer(data2));

        transfers = Transfer.create(transfers);

        for (Transfer transfer : transfers) {
            System.out.println(transfer);
        }
    }

    public static void findTransacaoById(String id) throws Exception {
        Transaction transaction = Transaction.get(id);
        System.out.println(transaction);
    }

    public static void getListQrCodeDinamicos() throws Exception {
        HashMap<String, Object> params = new HashMap<>();
        params.put("after", "2025-01-01");
        params.put("before", "2025-01-30");
        Generator<DynamicBrcode> brcodes = DynamicBrcode.query(params);

        for (DynamicBrcode brcode : brcodes) {
            System.out.println(brcode);
        }

    }

    // Taxa para PROCESSAR = 0,15
    // Taxa para LIQUIDAR  = 1%
    public static void createQrCodeDinamico(int valor) throws Exception {
        List<DynamicBrcode> brcodes = new ArrayList<>();
        HashMap<String, Object> data = new HashMap<>();
        data.put("amount", valor);
        data.put("expiration", 123456789);

        brcodes.add(new DynamicBrcode(data));
        brcodes = DynamicBrcode.create(brcodes);

        for (DynamicBrcode brcode : brcodes) {
            System.out.println(brcode);
        }
    }

    public static void createTransacaoEntreContas() throws Exception {
        // cria uma transacao
        List<Transaction> transactions = new ArrayList<>();
        HashMap<String, Object> data = new HashMap<>();
        data.put("amount", 10000);
        data.put("receiverId", "5651751147405412");
        data.put("description", "A Lannister always pays his debts");
        data.put("externalId", "my_unique_id");
        data.put("tags", new String[] { "lannister", "debts" });
        transactions.add(new Transaction(data));

        transactions = Transaction.create(transactions);

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public static void getTransacoes() throws Exception {
        // buscar transações
        HashMap<String, Object> params = new HashMap<>();
        params.put("after", "2025-01-01");
        params.put("before", "2025-01-30");
        com.starkbank.utils.Generator<Transaction> transactions = Transaction.query(params);

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public static void getSaldo() throws Exception {
        // Saldo em conta
        Balance balance = Balance.get();
        System.out.println(balance.toString());
    }
}
