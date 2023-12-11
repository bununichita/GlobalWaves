package main;

import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import command.input.Command;
import command.input.DoCommand;
import command.input.InitCommand;
import command.input.ParseCommand;
import fileio.input.LibraryInput;
import output.Output;
import store.data.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    static final String LIBRARY_PATH = CheckerConstants.TESTS_PATH + "library/library.json";

    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePathInput for input file
     * @param filePathOutput for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePathInput,
                              final String filePathOutput) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LibraryInput library = objectMapper.readValue(new File(LIBRARY_PATH), LibraryInput.class);
        LibraryInput.setInstance(library);

            ArrayNode outputs = objectMapper.createArrayNode();

        // TODO add your implementation
//        ***********************************************************************************

        System.out.println("new_test");

        ObjectMapper objectMapper1 = new ObjectMapper();
//
//
        String in = new String(CheckerConstants.TESTS_PATH);
//        System.out.println(in.concat(filePathInput));
//        String out = new String(CheckerConstants.OUT_FILE);
////
        List<InitCommand> unparsedCommands =
                objectMapper1.readValue(new File(in.concat(filePathInput)),
                        new TypeReference<List<InitCommand>>() { });
        List<Command> commands = new ArrayList<>();
        ParseCommand parseCommand = new ParseCommand(unparsedCommands, commands);

        parseCommand.action();
        commands = parseCommand.getParsedCommands();

        InitUserList initUserList = new InitUserList();
//        List<StoreUsers> users = initUserList.init(library.getUsers());
        StatisticsData statisticsData = StatisticsData.getInstance();
        statisticsData.setAllUsers(initUserList.init(library.getUsers()));
        SongsByLikes aux = new SongsByLikes();
        aux.initSongByLikeList(statisticsData.getAllSongsByLikes(), library.getSongs());

        List<Output> outputList = new ArrayList<>();

        DoCommand.makeAllCommands(commands, outputList);
        statisticsData.getAllUsers().clear();
        statisticsData.getAllPlaylists().clear();
        statisticsData.getAllAlbums().clear();
        statisticsData.getAllSongsByLikes().clear();



//        ***********************************************************************************

        ObjectWriter objectWriter = objectMapper1.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), outputList);
    }
}
