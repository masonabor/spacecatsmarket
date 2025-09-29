package com.edu.web.spacecatsmarket.ordering.domain;

import com.edu.web.spacecatsmarket.common.CommunicationChannel;
import lombok.Builder;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;

@Value
@Builder
public class CustomerDetails {

    CustomerDetailsId id;
    CustomerName name;
    Email email;
    PhoneNumber phoneNumber;
    String address;

    @Builder.Default
    Set<CommunicationChannel> preferredChannels = new HashSet<>();

    public void addPreferredChannels(Set<CommunicationChannel> channels) {
        this.preferredChannels.addAll(channels);
    }

    public void removePreferredChannels(Set<CommunicationChannel> channels) {
        this.preferredChannels.removeAll(channels);
    }
}
