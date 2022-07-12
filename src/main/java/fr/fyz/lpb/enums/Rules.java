package fr.fyz.lpb.enums;

public enum Rules {

	PAS_EMBROUILLE("💢 Embrouilles",
			"Ce serveur est fait pour rencontrer des gens, partager et s'amuser, donc pas d'embrouilles s'il vous plait."),
	RESPECT("🤝 Respect",
			"Le respect de chacun est une notion importante du serveur, vous devez respectez les choix et les avis de chacun pour que la bonne humeur sois présente."),
	SUGGESTIONS("💡 Suggestions",
			"N'hésitez pas à proposer des améliorations ! J'essayerais de les intégrés avec plaisir !"),
	TOUT_SUR_DISCORD_RESTE("🔒 Tout ce qui est ici reste ici",
			"Pour que ce discord reste le plus safe possible, tout ce qui est publié sur ce discord reste sur ce discord, vous ne devez pas screen de conversations pour nuire."),
	PRONOMS("💭 Pronoms et identitées", "Le respect des pronoms, de l'identitée et tout ce qui concerne une personne est à respecter, l'homophobie, la transphobie, l'enbyphobie, le racisme et tout autre sorte de discrimination sont totalement interdites."),
	GUIDELINES("📏 Règlement de Discord", "Le règlement et les guidelines de discord sont à respecter https://discord.com/guidelines"),
	OTHER("🔨 Décisions de l'équipe", "Même si cela ne rentre pas directement dans le règlement, si votre comportement semble inadapté au Discord, l'équipe se réserve le droit de vous sanctionner ou de vous rappeler à l'ordre.");
	
	private String title, desc;

	private Rules(String title, String desc) {
		this.title = title;
		this.desc = desc;
	}

	public String geTitle() {
		return title;
	}

	public String getDesc() {
		return desc;
	}

}
