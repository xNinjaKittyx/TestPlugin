package io.github.firefwing24.TestPlugin;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PEXRankCheck {

	public PEXRankCheck() {
		// TODO Auto-generated constructor stub
	}

	public boolean isGreater(Player player, Player target) {
		// Returns true if and only if player's Rank is lower than target.
		// If Ranks equal, return false;
		int playerRank = PermissionsEx.getUser(player).getGroups()[0].getRank();
		int targetRank = PermissionsEx.getUser(player).getGroups()[0].getRank();
		if (playerRank < targetRank)
			return true;
		else
			return false;
	}

	public boolean isLess(Player player, Player target) {
		// Returns true if and only if player's Rank is lower than target.
		// If Ranks Equal, return false;
		int playerRank = PermissionsEx.getUser(player).getGroups()[0].getRank();
		int targetRank = PermissionsEx.getUser(player).getGroups()[0].getRank();
		if (playerRank > targetRank)
			return true;
		else
			return false;
	}

	public boolean isEqual(Player player, Player target) {
		// Returns true if and only if player's Rank is same as target.
		int playerRank = PermissionsEx.getUser(player).getGroups()[0].getRank();
		int targetRank = PermissionsEx.getUser(player).getGroups()[0].getRank();
		if (playerRank == targetRank)
			return true;
		else
			return false;
	}

}
