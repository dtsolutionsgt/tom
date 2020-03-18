package com.dts.classes.Transacciones.Stock.Stock_se_rec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeStock_se_recList {
    @ElementList(inline=true)
    public List<clsBeStock_se_rec> items = new List<clsBeStock_se_rec>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<clsBeStock_se_rec> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(clsBeStock_se_rec clsBeStock_se_rec) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends clsBeStock_se_rec> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeStock_se_rec> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public clsBeStock_se_rec get(int index) {
            return null;
        }

        @Override
        public clsBeStock_se_rec set(int index, clsBeStock_se_rec element) {
            return null;
        }

        @Override
        public void add(int index, clsBeStock_se_rec element) {

        }

        @Override
        public clsBeStock_se_rec remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<clsBeStock_se_rec> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeStock_se_rec> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeStock_se_rec> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}