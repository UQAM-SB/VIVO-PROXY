package ca.uqam.tool.vivoproxy.pattern.command;

public abstract class Command {
	protected String name;
	private CommandResult commandResult;


	public CommandResult execute(Receiver receiver){
		return null;};

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

		public CommandResult getCommandResult() {
			return commandResult;
		}

		public void setCommandResult(CommandResult commandResult) {
			this.commandResult = commandResult;
		}
}
