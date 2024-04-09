package com.github.navust.gtcli;

import com.github.navust.gtcli.subcommand.Codes;
import com.github.navust.gtcli.subcommand.Redeem;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(
        name = "gt-cli",
        mixinStandardHelpOptions = true,
        version = "gt-cli 1.0.0",
        description = "A set of CLI utilities for Guardian Tales.",
        subcommands = {
                Codes.class,
                Redeem.class
        }
)
public class GtCli implements Callable<Integer> {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new GtCli()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);
        return 0;
    }
}
